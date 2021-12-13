class Location {
	String locationName;
	String prompt;
	Room location;
}
public class Room {
	enum LocationMode {
		compass, map
	}
	enum EventType {
		roomEnter, roomLeave, roomLookaround
	}
	public String enterPrompt = "";
	String description;

	// Room contents
	Interactive[] roomInteractives = new Interactive[16];

	// Rooms for 'go out' or 'go north'
	String[] directions = {"north", "south", "west", "east", "up", "down", "out", "in"};
	String[] compassPrompts = new String[directions.length];
	Room[] compassRooms = new Room[directions.length];
	
	// Rooms for 'enter bedroom' or 'enter cave'
	String[] mapLocations = new String[16];
	String[] mapPrompts = new String[mapLocations.length];
	Room[] mapRooms = new Room[mapLocations.length];

	// Room events
	String[] compassEvents = new String[directions.length];
	String[] mapEvents = new String[mapLocations.length];
	String roomEnterEvent;
	String roomLeaveEvent;
	String roomLookaroundEvent;
	
	public Room(String description) {
		this.description = description;
	}
	public void addEvent(EventType eventType, String eventName){
		switch (eventType){
			case roomEnter:
				roomEnterEvent = eventName;
				break;
			case roomLeave:
				roomLeaveEvent = eventName;
				break;
			case roomLookaround:
				roomLookaroundEvent = eventName;
				break;
		}
	}
	public String getEvent(EventType eventType){
		switch (eventType){
			case  roomEnter:
				return roomEnterEvent;
			case roomLeave:
				return roomLeaveEvent;
			case roomLookaround:
				return roomLookaroundEvent;
			default:
				return "";
		}
	}
	public void addLocationEvent(LocationMode mode, String name, String eventName) {
		int index;
		if(mode == LocationMode.compass){
			index = Utils.findIndex(directions, name);
			if(index != -1){
				compassEvents[index] = eventName;
			}
		}
		else{
			index = Utils.findIndex(mapLocations, name);
			if(index != -1){
				mapEvents[index] = eventName;
			}
		}
	}
	public String getLocationEvent(LocationMode mode, String name) {
		int index;
		if(mode == LocationMode.compass){
			index = Utils.findIndex(directions, name);
			if(index != -1){
				return compassEvents[index];
			}
		}
		else{
			index = Utils.findIndex(mapLocations, name);
			if(index != -1){
				return mapEvents[index];
			}
		}
		return "";
	}
	public void addLocation(LocationMode mode, String name, Room room) {
		if(mode == LocationMode.compass){
			int index = Utils.findIndex(this.directions, name);
			if(index != -1){
				compassRooms[index] = room;
			}	
		}
		else{
			for(int i = 0; i < mapLocations.length; i++){
				if(name.equals(mapLocations[i]) || mapLocations[i] == null){
					mapLocations[i] = name;
					mapRooms[i] = room;
					break;
				}
			}
		}
	}
	public void addLocation(LocationMode mode, String name, Room room, String prompt) {
		if(mode == LocationMode.compass){
			int index = Utils.findIndex(this.directions, name);
			if(index != -1){
				compassRooms[index] = room;
				compassPrompts[index] = prompt;
			}
		}
		else{
			for(int i = 0; i < mapLocations.length; i++){
				if(name.equals(mapLocations[i]) || mapLocations[i] == null){
					mapLocations[i] = name;
					mapRooms[i] = room;
					mapPrompts[i] = prompt;
					break;
				}
			}
		}
	}
	public Room getLocation(LocationMode mode, String name) {
		int index;
		if(mode == LocationMode.compass){
			index = Utils.findIndex(this.directions, name);
			if(index != -1){
				if(compassPrompts[index] != null) {
					compassRooms[index].enterPrompt = compassPrompts[index];
				}
				return compassRooms[index];
			}
		}
		else{
			index = Utils.findIndex(this.mapLocations, name);
			if(index != -1){
				if(mapPrompts[index] != null) {
					mapRooms[index].enterPrompt = mapPrompts[index];
				}
				return mapRooms[index];
			}
		}
		return new Room("");
	}
	public String[] getLocations(LocationMode mode){
		String[] validExits;
		String[] locations = directions;
		Room[] rooms = compassRooms;
		if(mode == LocationMode.map){
			locations = mapLocations;
			rooms = mapRooms;
		}
		int count = 0;
		for(Room room: rooms) {
			if(room != null) {
				count++;
			}
		}
		
		validExits = new String[count];
		count = 0;
		for(int i = 0; i < rooms.length; i++) {
			if(rooms[i] != null) {
				validExits[count] = locations[i];
				count++;
			}
		}
		return validExits;
	}
	public void addInteractive(Interactive interactive){
		for(int i = 0; i < roomInteractives.length; i++){
			if(roomInteractives[i] != null && roomInteractives[i].name == interactive.name){
				roomInteractives[i] = interactive;
				break;
			}
			else if(roomInteractives[i] == null){
				roomInteractives[i] = interactive;
				break;
			}
		}
	}
	public String[] getInteractives(){
		String[] interactives;
		int i, count = 0;
		for(i = 0; i < roomInteractives.length; i++){
			if(roomInteractives[i] != null){
				count++;
			}
		}
		interactives = new String[count];
		count = 0;
		for(i = 0; i < roomInteractives.length; i++){
			if(roomInteractives[i] != null){
				interactives[count] = roomInteractives[i].name;
				count++;
			}
		}
		return interactives;
	}
	public Interactive getInteractive(String name){
		for(int i = 0; i < roomInteractives.length; i++){
			if(roomInteractives[i] != null && name.equals(roomInteractives[i].name)){
				return roomInteractives[i];
			}
		}
		return new Interactive("", "");
	}
}
