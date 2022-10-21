package dao;

public class User {
        private String id;
        private String name;
        private String password;
        //매개변수 없이 호출
        public User() {
        }

        public User(String id, String name, String password) {
            this.id = id;
            this.name = name;
            this.password = password;
        }

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getPassword() {
            return password;
        }
}
