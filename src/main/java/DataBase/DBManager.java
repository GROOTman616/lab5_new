package DataBase;

import Common.User;
import Data.*;

import java.sql.*;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class DBManager {
    private final Connection connection;

    public DBManager(Connection connection) {
        this.connection = connection;
    }

    // ---------- Пользователи ----------
    public boolean userExists(String login) {
        try (PreparedStatement statement = connection.prepareStatement(
                "SELECT 1 FROM users WHERE login = ?")) {
            statement.setString(1, login);
            ResultSet res = statement.executeQuery();
            return res.next();
        } catch (SQLException e) {
            System.err.println("Ошибка проверки существования пользователя: " + e.getMessage());
            return false;
        }
    }

    public boolean checkPassword(String login, String password) {
        try (PreparedStatement statement = connection.prepareStatement(
                "SELECT 1 FROM users WHERE login = ? AND password = ?")) {
            statement.setString(1, login);
            statement.setString(2, password);
            ResultSet res = statement.executeQuery();
            return res.next();
        } catch (SQLException e) {
            System.err.println("Ошибка проверки пароля: " + e.getMessage());
            return false;
        }
    }

    public boolean registerUser(User user) {
        if (userExists(user.getLogin())) return false;
        return insertUser(user);
    }

    private boolean insertUser(User user) {
        try (PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO users(login, password) VALUES (?, ?)")) {
            statement.setString(1, user.getLogin());
            statement.setString(2, user.getPassword());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Ошибка добавления пользователя: " + e.getMessage());
            return false;
        }
    }

    private int getUserId(String login) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(
                "SELECT id FROM users WHERE login = ?")) {
            statement.setString(1, login);
            ResultSet res = statement.executeQuery();
            if (res.next()) {
                return res.getInt("id");
            }
        }
        throw new SQLException("Пользователь не найден");
    }

    private String getUserLoginByFlatId(long flatId) {
        try (PreparedStatement statement = connection.prepareStatement(
                "SELECT u.login FROM flats f JOIN users u ON f.user_id = u.id WHERE f.id = ?")) {
            statement.setLong(1, flatId);
            ResultSet res = statement.executeQuery();
            if (res.next()) {
                return res.getString("login");
            }
        } catch (SQLException e) {
            System.err.println("Ошибка получения владельца квартиры: " + e.getMessage());
        }
        return null;
    }

    // ---------- Квартиры ----------
    public long insertFlat(Flat flat, User user) {
        try {
            if (!checkPassword(user.getLogin(), user.getPassword())) return -1;

            int userId = getUserId(user.getLogin());

            // Coordinates
            long coordinatesId = insertCoordinates(flat.getCoordinates());

            // House
            long houseId = insertHouse(flat.getHouse());

            // Flat
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO flats (name, coordinates_id, creation_date, area, number_of_rooms, price, view, transport, house_id, user_id) " +
                            "VALUES (?, ?, ?, ?, ?, ?, ?::view, ?::transport, ?, ?) RETURNING id",
                    Statement.RETURN_GENERATED_KEYS
            );

            statement.setString(1, flat.getName());
            statement.setLong(2, coordinatesId);
            statement.setObject(3, flat.getCreationDate());
            statement.setFloat(4,flat.getArea());
            statement.setLong(5, flat.getNumberOfRooms());
            if (flat.getPrice() != null) {
                statement.setInt(6, flat.getPrice());
            } else {
                statement.setNull(6, Types.INTEGER);
            }
            statement.setString(7, flat.getView().name());
            statement.setString(8, flat.getTransport().name());
            statement.setLong(9, houseId);
            statement.setInt(10, userId);

            statement.executeUpdate();
            ResultSet res = statement.getGeneratedKeys();
            if (res.next()) {
                return res.getLong(1);
            }
        } catch (SQLException e) {
            System.err.println("Ошибка добавления квартиры: " + e.getMessage());
        }
        return -1;
    }

    private long insertCoordinates(Coordinates coordinates) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO coordinates (x, y) VALUES (?, ?) RETURNING id",
                Statement.RETURN_GENERATED_KEYS
        );
        statement.setLong(1, coordinates.getX());
        statement.setLong(2, coordinates.getY());
        statement.executeUpdate();
        ResultSet res = statement.getGeneratedKeys();
        if (res.next()) return res.getLong(1);
        throw new SQLException("Не удалось вставить координаты");
    }

    private long insertHouse(House house) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO houses (name, year, number_of_floors, number_of_flats_on_floor, number_of_lifts) " +
                        "VALUES (?, ?, ?, ?, ?) RETURNING id",
                Statement.RETURN_GENERATED_KEYS
        );
        if (house.getName() != null) {
            statement.setString(1, house.getName());
        } else {
            statement.setNull(1, Types.VARCHAR);
        }
        statement.setLong(2, house.getYear());
        statement.setLong(3, house.getNumberOfFloors());
        statement.setLong(4, house.getNumberOfFlatsOnFloor());
        statement.setLong(5, house.getNumberOfLifts());

        statement.executeUpdate();
        ResultSet res = statement.getGeneratedKeys();
        if (res.next()) return res.getLong(1);
        throw new SQLException("Не удалось вставить дом");
    }

    public PriorityQueue<Flat> loadFlats() {
        PriorityQueue<Flat> flats = new PriorityQueue<>();
        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM flats")) {
            ResultSet res = statement.executeQuery();
            while (res.next()) {
                long id = res.getLong("id");
                String name = res.getString("name");
                ZonedDateTime creationDate = res.getObject("creation_date", ZonedDateTime.class);
                float area = res.getFloat("area");
                long numberOfRooms = res.getLong("number_of_rooms");
                Integer price = (Integer) res.getObject("price");

                View view = View.valueOf(res.getString("view"));
                Transport transport = Transport.valueOf(res.getString("transport"));

                long coordinatesId = res.getLong("coordinates_id");
                Coordinates coordinates = getCoordinatesById(coordinatesId);

                long houseId = res.getLong("house_id");
                House house = getHouseById(houseId);

                Flat flat = new Flat(name, coordinates, area, numberOfRooms, price, view, transport, house);
                flat.setId(id);
                flats.add(flat);
            }
        } catch (SQLException e) {
            System.err.println("Ошибка загрузки коллекции: " + e.getMessage());
        }
        return flats;
    }

    private Coordinates getCoordinatesById(long id) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(
                "SELECT * FROM coordinates WHERE id = ?"
        );
        statement.setLong(1, id);
        ResultSet res = statement.executeQuery();
        if (res.next()) {
            return new Coordinates(res.getLong("x"), res.getLong("y"));
        }
        throw new SQLException("Не удалось найти координаты");
    }

    private House getHouseById(long id) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(
                "SELECT * FROM houses WHERE id = ?"
        );
        statement.setLong(1, id);
        ResultSet res = statement.executeQuery();
        if (res.next()) {
            return new House(
                    res.getString("name"),
                    res.getLong("year"),
                    res.getLong("number_of_floors"),
                    res.getLong("number_of_flats_on_floor"),
                    res.getLong("number_of_lifts")
            );
        }
        throw new SQLException("Не удалось найти дом");
    }

    public boolean removeFlatById(long id, User user) {
        try {
            if (!checkPassword(user.getLogin(), user.getPassword())) return false;
            String owner = getUserLoginByFlatId(id);
            if (!user.getLogin().equals(owner)) return false;

            PreparedStatement statement = connection.prepareStatement(
                    "DELETE FROM flats WHERE id = ?"
            );
            statement.setLong(1, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Ошибка удаления квартиры: " + e.getMessage());
            return false;
        }
    }

    public boolean updateFlat(long id, Flat newFlat, User user) {
        try {
            if (!checkPassword(user.getLogin(), user.getPassword())) return false;
            String owner = getUserLoginByFlatId(id);
            if (!user.getLogin().equals(owner)) return false;

            PreparedStatement statement = connection.prepareStatement(
                    "UPDATE flats SET name=?, area=?, number_of_rooms=?, price=?, view=?::view, transport=?::transport " +
                            "WHERE id=?"
            );
            statement.setString(1, newFlat.getName());
            statement.setFloat(2, newFlat.getArea());
            statement.setLong(3, newFlat.getNumberOfRooms());
            if (newFlat.getPrice() != null) {
                statement.setInt(4, newFlat.getPrice());
            } else {
                statement.setNull(4, Types.INTEGER);
            }
            statement.setString(5, newFlat.getView().name());
            statement.setString(6, newFlat.getTransport().name());
            statement.setLong(7, id);

            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Ошибка обновления квартиры: " + e.getMessage());
            return false;
        }
    }

    public boolean clearFlats(User user) {
        try {
            if (!checkPassword(user.getLogin(), user.getPassword())) return false;
            int userId = getUserId(user.getLogin());

            PreparedStatement statement = connection.prepareStatement(
                    "DELETE FROM flats WHERE user_id = ?"
            );
            statement.setInt(1, userId);
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Ошибка очистки коллекции: " + e.getMessage());
            return false;
        }
    }

    public boolean isFlatOwnedByUser(long flatId, User user) {
        try {
            return user.getLogin().equals(getUserLoginByFlatId(flatId));
        } catch (Exception e) {
            return false;
        }
    }
}
