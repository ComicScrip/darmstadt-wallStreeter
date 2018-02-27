import java.util.HashMap;
import java.util.Map;

public class Message {
    final static String MESSAGE_DELIMIER = "---------------";
    final static String KV_DELIMITER = "=";
    final static String FIELD_DELIMIER = "\n";
    final static String HEADER_DELIMITER = "\n\n";
    private Map<MessageHeaderField, String> header = new HashMap<MessageHeaderField, String>();
    private Map<String, String> body = new HashMap<String, String>();

    public Message(){ }

    public Message(String strRepresentation) {
        String header
        String fieldAssignements[] = strRepresentation.split("\n");
        String assignementParts[] = null;
        String fieldName = null;
        String fieldValue = null;
        for (String fieldAssignement: fieldAssignements) {
            assignementParts = fieldAssignement.split("=");
            if(assignementParts.length != 2) break;
            fieldName = assignementParts[0];
            fieldValue = assignementParts[1];

            switch (fieldName) {
                case "stockName": setStock(new Stock(StockName.valueOf(fieldValue)));
                case "price": setPrice(Double.parseDouble(fieldValue));
                case "status": setStatus(StockActionStatus.valueOf(fieldValue));
                case "uuid": setUUID(UUID.fromString(fieldValue));
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
