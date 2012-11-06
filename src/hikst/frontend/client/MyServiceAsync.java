package hikst.frontend.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface MyServiceAsync
{
    //public void myMethod(String mailFrom, String mailTo, String mailSub, String mailMes, AsyncCallback callback);
    public void sendMail(String mailFrom, String mailFromPass, String mailTo, String mailSub, String mailMes, AsyncCallback callback);
}
