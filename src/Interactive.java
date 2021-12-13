public class Interactive{
    enum EventType {
        lookAt, activation
    }
    public String description;
    public String name;
    String lookAtEvent = "";
    String activationEvent = "";
    public Interactive(String name, String description){
        this.name = name;
        this.description = description;
    }
    public Interactive(String name){
        this.name = name;
        this.description = "Nothing out of the ordinary.";
    }
    public void addEvent(EventType eventType, String eventName){
        switch (eventType){
            case lookAt:
                lookAtEvent = eventName;
                break;
            case activation:
                activationEvent = eventName;
                break;
        }
    }
    public String getEvent(EventType eventType){
        switch (eventType){
            case lookAt:
                return lookAtEvent;
            case activation:
                return activationEvent;
            default:
                return "";

        }
    }
}
