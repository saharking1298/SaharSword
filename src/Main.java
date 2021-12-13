import java.util.Scanner;
import java.util.Arrays;

public class Main {
	static Scanner input = new Scanner(System.in);
	static Room currentRoom;
	static String[] commands = {"go", "enter", "look", "map", "use", "inventory"};
	static String[] commandDescriptions = {
			"Move to a certain direction.\nValid directions: north, south, west, east, up, down, in, out.\nSyntax: go [direction]",
			"Enter a certain location. To see a list of locations, use 'map' command.\nUsage: 'enter [locationName]'.",
			"Look at the room or a specific item. To look at an item, type the item's name.\nSyntax: look [item]",
			"Display a list of all locations and directions you can go to.\nIdentical commands: 'locations', 'directions'",
			"Activate an interactive. Use 'map' command to see a list of all interactives.\n" +
					"Syntax: 'use [interactiveName]'.\nIdentical commands: 'activate'.",
			"See the contents of your inventory. Identical commands: 'backpack', 'i'."};
	
	public static void main(String[] args) {
		String userInput;
		System.out.println("Welcome to SaharSword!");
		System.out.println("Type 'help' to get a list of valid commands.\n");
		test();
		while (true) {
			userInput = input.nextLine();
			react(userInput);
		}
	}
	public static void test() {
		Room demo = Demo.houseDemo();
		updateCurrentRoom(demo);
	}
	public static void updateCurrentRoom(Room room) {
		if(currentRoom != null){
			sendEvent(currentRoom.getEvent(Room.EventType.roomLeave));
		}
		sendEvent(room.getEvent(Room.EventType.roomEnter));
		if(!room.enterPrompt.equals("")){
			System.out.println(room.enterPrompt);
			room.enterPrompt = "";
		}
		System.out.println(room.description);
		currentRoom = room;
	}
	public static boolean sendEvent(String eventName){
		// Return true if changed rooms
		if(eventName == null){
			eventName = "";
		}
		if(!eventName.equals("")) {
			Room room =  Demo.eventHandler(eventName);
			if(!room.description.equals("")){
				updateCurrentRoom(room);
				return true;
			}
		}
		return false;
	} 
	public static void mapCommand(){
		String[] mapLocations = currentRoom.getLocations(Room.LocationMode.map);
		String[] compassLocations = currentRoom.getLocations(Room.LocationMode.compass);
		String[] roomInteractives = currentRoom.getInteractives();
		if(compassLocations.length > 0){
			System.out.println("You can go: " + String.join(", ", compassLocations));
		}
		if(mapLocations.length > 0){
			System.out.println("You can enter: " + String.join(", ", mapLocations));
		}
		if(roomInteractives.length > 0){
			System.out.println("You can activate: " + String.join(", ", roomInteractives));
		}
	}
	public static void goCommand(String direction) {
		String[] validDirections = {"north", "south", "west", "east", "up", "down", "out", "in"};
		String[] directionShortcuts = {"n", "s", "w", "e", "u", "d", "o", "i"};
		boolean isShortcut = Utils.contains(directionShortcuts, direction);
		if(Utils.contains(validDirections, direction) || isShortcut) {
			if(isShortcut){
				direction = validDirections[Utils.findIndex(directionShortcuts, direction)];
			}
			if(Utils.contains(currentRoom.getLocations(Room.LocationMode.compass), direction)) {
				sendEvent(currentRoom.getLocationEvent(Room.LocationMode.compass, direction));
				updateCurrentRoom(currentRoom.getLocation(Room.LocationMode.compass, direction));
			}
			else {
				System.out.println("You can't go there!");
			}
		}
		else {
			System.out.println("Invalid direction: '" + direction + "'.");
		}
	}
	public static void helpCommand() {
		System.out.println("Type 'help [command]' to get info on a specific command.");
		System.out.println("List of available commands:");
		for(String command: commands) {
			System.out.println("- " + command);
		}
	}
	public static void helpCommand(String commandName) {
		commandName = commandName.toLowerCase();
		int index = Utils.findIndex(commands, commandName);
		if(index >= 0) {
			System.out.println("---- " + commandName + ": Command Description: ----");
			System.out.println(commandDescriptions[index]);
		}
		else {
			System.out.println("No such command: '" + commandName + "'.");
		}
	}
	public static void lookCommand() {
		System.out.println("You look around.");
		sendEvent(currentRoom.getEvent(Room.EventType.roomLookaround));
		System.out.println(currentRoom.description);
	}
	public static void lookCommand(String lookAt) {
		lookAt = lookAt.toLowerCase();
		String[] roomView = {"around", "room"};
		if(Utils.contains(roomView, lookAt)){
			lookCommand();
		}
		else{
			if(Utils.contains(currentRoom.getInteractives(), lookAt)){
				Interactive interactive = currentRoom.getInteractive(lookAt);
				String event = interactive.getEvent(Interactive.EventType.lookAt);
				sendEvent(event);
				System.out.println(interactive.description);
			}
			else{
				System.out.println("No such interactive: '" + lookAt + "'.");
			}
		}
	}
	public static void enterCommand(String location){
		location = location.toLowerCase();
		if (Utils.contains(currentRoom.getLocations(Room.LocationMode.map), location)){
			sendEvent(currentRoom.getLocationEvent(Room.LocationMode.map, location));
			Room room = currentRoom.getLocation(Room.LocationMode.map, location);
			updateCurrentRoom(room);
		}
		else{
			System.out.println("No such location: '" + location + "'.");
		}
	}
	public static void activateCommand(String interactiveName){
		Interactive interactive = currentRoom.getInteractive(interactiveName);
		if(!interactive.name.equals("")){
			String event = interactive.getEvent(Interactive.EventType.activation);
			boolean roomChanged = sendEvent(event);
			if(event.equals("")){
				System.out.println("This interactive does nothing.");
			}
			else if(!roomChanged){
				System.out.println("\n" + currentRoom.description);
			}
		}
		else{
			System.out.println("No such interactive: '" + interactiveName + "'.");
		}
	}
	public static String getFullArg(String[] words){
		int startIndex = 0;
		if(words.length > 1){
			startIndex = 1;
		}
		return getFullArg(words, startIndex);
	}
	public static String getFullArg(String[] words, int startIndex){
		return String.join(" ", Arrays.copyOfRange(words, 1, words.length));
	}
	public static void react(String userInput) {
		userInput = userInput.trim();
		String[] words = userInput.split(" +");
		String command = words[0].toLowerCase();
		Boolean valid = true;
		String arg = getFullArg(words);
		if(words.length > 1) {
			switch(command) {
				case "help":
					helpCommand(arg);
					break;
				case "go":
					goCommand(arg);
					break;
				case "enter":
				case "goto":
					enterCommand(arg);
					break;
				case "use":
				case "activate":
					activateCommand(arg);
					break;
				case "look":
				case "lookat":
				case "see":
					if(words.length > 2 && words[1].toLowerCase().equals("at")){
						lookCommand(getFullArg(words, 2));
					}
					else {
						lookCommand(arg);
					}
					break;
				default:
					valid = false;
					break;
			}
		}
		else {
			switch(command) {
				case "help":
					helpCommand();
					break;
				case "look":
					lookCommand();
					break;
				case "locations":
				case "directions":
				case "map":
					mapCommand();
					break;
				case "inventory":
				case "backpack":
				case "i":
					break;
				default:
					valid = false;
					break;
			}
		}
	}
}