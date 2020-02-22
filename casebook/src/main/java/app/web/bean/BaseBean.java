package app.web.bean;


import javax.faces.context.FacesContext;
import java.io.IOException;

public class BaseBean {

    protected BaseBean(){

    }

    protected void redirect(String url) {
        try {
            FacesContext
                    .getCurrentInstance()
                    .getExternalContext()
                    .redirect("/views" + url + ".jsf");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
