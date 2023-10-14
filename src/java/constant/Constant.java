package constant;

public interface Constant {

     String GOOGLE_CLIENT_ID = "251246115701-72lb20756f75526cfuj13rbv9g3h2jfq.apps.googleusercontent.com";

     String GOOGLE_CLIENT_SECRET = "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";

     String GOOGLE_REDIRECT_URI = "http://localhost:9999/isp392-project/LoginGoogleController";

     String GOOGLE_LINK_GET_TOKEN = "https://accounts.google.com/o/oauth2/token";

     String GOOGLE_LINK_GET_USER_INFO = "https://www.googleapis.com/oauth2/v1/userinfo?access_token=";

     String GOOGLE_GRANT_TYPE = "authorization_code";
    
     String GOOGLE_URL_LOGIN ="https://accounts.google.com/o/oauth2/auth?scope=email profile&redirect_uri=http://localhost:9999/isp392-project/LoginGoogleController&response_type=code&client_id=251246115701-72lb20756f75526cfuj13rbv9g3h2jfq.apps.googleusercontent.com&approval_prompt=force";
}
