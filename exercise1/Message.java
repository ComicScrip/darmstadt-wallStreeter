import java.util.Dictionary;
import java.util.HashMap;
import java.util.Iterator;

public class Message {
    final static String MESSAGE_DELIMIER = "---------------";
    final static String KV_DELIMITER = "=";
    final static String FIELD_DELIMIER = "\n";
    final static String HEADER_DELIMITER = "\n\n";
    private Dictionary<MessageHeaderField, String> header;
    private Dictionary<String, String>  body;

    @Override
    public String toString() {
        StringBuilder h = new StringBuilder();

        Iterator<String> hi = header.elements().asIterator();
        String hk;
        while(hi.hasNext()){
            hk = hi.next();
            h.append(hk + KV_DELIMITER + header.get(hk));
        }

        StringBuilder b = new StringBuilder();

        Iterator<String> bi = body.elements().asIterator();
        String bk;
        while(bi.hasNext()){
            bk = bi.next();
            b.append(bk + KV_DELIMITER + header.get(bk));
        }

        return MESSAGE_DELIMIER + h.toString() + HEADER_DELIMITER + b.toString() + MESSAGE_DELIMIER;
    }

    private void setHeaderField(String key, String value) {
        this.header.put(key, value);
    }

    public void setHeaderField()

    public void setBodyParam(String key, String value) {
        this.body.put(key, value);
    }
}
