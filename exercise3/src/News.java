public class News {
    private NewsType type;
    private StockName about;
    private String message;

    public News(NewsType type,StockName about)
    {
        this.type = type;
        this.about = about;
        message = buildNewsMessage();
    }

    static News parseFromPublisherMessage(String msg) throws Exception{
        String parts[] = msg.split(":");
        if(parts.length != 2) throw new Exception("malformed message");
        NewsType nt = parts[0].contains("Good") ? NewsType.GOOD : NewsType.BAD;
        StockName sn = StockName.valueOf(parts[1]);
        return new News(nt, sn);
    }

    private String buildNewsMessage()
    {

        if(type.equals(NewsType.GOOD))
        {
            return "Good news about :" + about.name();
        }
        else if (type.equals(NewsType.BAD))
        {
            return "Bad news about :" + about.name();
        }
        else
        {
            return "No news for :" + about.name();
        }
    }

    public String getMessage() {
        return message;
    }

    public NewsType getType() {
        return type;
    }

    public void setType(NewsType type) {
        this.type = type;
    }

    public StockName getAbout() {
        return about;
    }

    public void setAbout(StockName about) {
        this.about = about;
    }
}
