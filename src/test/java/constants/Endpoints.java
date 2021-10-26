package constants;

public class Endpoints {

    public static final String POST_LOGIN = "auth/api/login/";


    //App Endpoints
    public static final String APP_GET_ALL_ORGANIZATIONS = "/api/organizations/";

    public static final String APP_GET_ALL_PROJECTS = "/api/projects/";

    public static final String APP_GET_SPECIFIC_PROJECT = "/api/projects/{projectId}/";

    public static final String APP_GET_LOGGED_IN_USER_DETAILS = "/auth/api/users/{userId}";

    //Widget Endpoints
    public static final String WIDGET_CONVERSATION_ENDPOINT = "/v3.2/projects/{projectId}/conversations";


}
