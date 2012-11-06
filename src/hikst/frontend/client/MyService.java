package hikst.frontend.client;

import com.google.gwt.user.client.rpc.RemoteService;

public interface MyService extends RemoteService
{
    //public String myMethod (String mailFrom, String mailTo, String mailSub, String mailMes);
    public boolean sendMail(String mailFrom, String mailFromPass, String mailTo, String mailSub, String mailMes);
}