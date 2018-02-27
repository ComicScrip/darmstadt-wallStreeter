import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Message {
    final static String MESSAGE_DELIMIER = "---------------";
    final static String KV_DELIMITER = "=";
    final static String FIELD_DELIMIER = "\n";
    final static String HEADER_DELIMITER = "\n\n";
    private Map<MessageHeaderField, String> header = new HashMap<MessageHeaderField, String>();
    private Map<String, String> body = new HashMap<String, String>();

    public Message(){ }

    public Message(String strRepresentation) {
        String messageParts[] = strRepresentation.split(HEADER_DELIMITER);
        parseFields(messageParts[0], true);
        parseFields(messageParts[1], false);

    }

    private void parseFields(String str, boolean header){
        String fieldAssignements[] = str.split(FIELD_DELIMIER);
        String assignementParts[] = null;
        String fieldName = null;
        String fieldValue = null;

        for (String fieldAssignement: fieldAssignements) {
            assignementParts = fieldAssignement.split(KV_DELIMITER);
            if(assignementParts.length != 2) break;
            fieldName = assignementParts[0];
            fieldValue = assignementParts[1];

            if(header) {
                setHeaderField(MessageHeaderField.valueOf(fieldName), fieldValue);
            } else {
                setBodyParam(fieldName, fieldValue);
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder h = new StringBuilder();
        StringBuilder b = new StringBuilder();

        for (Object o : header.entrySet()) {
            Map.Entry pair = (Map.Entry) o;
            h.append(pair.getKey() + KV_DELIMITER + pair.getValue() + FIELD_DELIMIER);
        }

        for (Object o : body.entrySet()) {
            Map.Entry pair = (Map.Entry) o;
            b.append(((MessageHeaderField) pair.getKey()).name() + KV_DELIMITER + pair.getValue() + FIELD_DELIMIER);
        }

        return MESSAGE_DELIMIER + h.toString() + HEADER_DELIMITER + b.toString() + MESSAGE_DELIMIER;
    }

    public void setHeaderField(MessageHeaderField k, String v){
        header.put(k, v);
    }

    public void setBodyParam(String key, String value) {
        body.put(key, value);
    }

    public Map<MessageHeaderField, String> getHeader() {return header;}
    public Map<String, String> getBody() {return body;}
}
