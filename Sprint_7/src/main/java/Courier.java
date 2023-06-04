package ru.yandex.praktikum.couriers;
//cоздаем класс для курьера
public class Courier {

    private String id;
    private String login;
    private String password;
    private String firstName;
    //создаем конструктор с параметрами
    public Courier(String Login, String password, String firstName) {
        this.login = login;
        this.password = password;
        this firstName = firstName;
        //создаем конструктор без параметров
        public Courier() {}
//геттеры и сеттеры для всех полей
        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getLogin() {
            return login;
        }

        public void setLogin(String login) {
            this.login = login;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

    }
}