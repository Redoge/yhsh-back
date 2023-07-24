package app.redoge.yhshback.utill.consts;

public class Paths {
    private Paths() {}
    public static final String API_VERSION_PATH = "/api/v1/";
    public static final String USERS_PATH = API_VERSION_PATH + "users";
    public static final String TRAININGS_PATH = API_VERSION_PATH + "trainings";
    public static final String ACTIVITIES_PATH = API_VERSION_PATH + "activities";
    public static final String ACTIVATION_PATH = API_VERSION_PATH + "activate";
    public static final String LOGINS_PATH = API_VERSION_PATH + "logins";
    public static final String AUTH_PATH = API_VERSION_PATH + "auth";
    public static final String WORKOUT_PATH = API_VERSION_PATH + "workouts";

    public static final String ADMIN_PATH = API_VERSION_PATH + "admin/";
    public static final String ADMIN_USER_PATH = ADMIN_PATH + "users";
}
