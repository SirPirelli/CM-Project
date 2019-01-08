package goldenegg.detectivephiladelphia.Models;

import java.util.Date;
import java.util.UUID;

public class Notes {

    /* FIELDS */
    String  title;
    String  description;
    String  keyID;
/*------------------------------*/

    /* PROPERTIES */
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getKeyID(){return keyID;}

    public void setKeyID(String keyID) { this.keyID = keyID; }

    /*---------------------------*/

    /* CONSTRUCTORS */
    public Notes() {
        this.title     = "";
        this.description   = "";
    }

    public Notes(String titulo, String descricao) {
        this.title     = titulo;
        this.description  = descricao;
    }
    /* ---------------------- */
}
