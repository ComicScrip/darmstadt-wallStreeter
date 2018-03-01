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

    private String buildNewsMessage()
    {

        if(type.equals(NewsType.GOOD))
        {
            return "Good news about " + about.name();
        }
        else if (type.equals(NewsType.BAD))
        {
            return "Bad news about " + about.name();
        }
        else
        {
            return "No news for " + about.name();
        }
    }

    public String getMessage() {
        return message;
    }
}
