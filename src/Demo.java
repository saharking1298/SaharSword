class House{
    // TODO: You can buy guns from ebay, and then fight the FBI.
    // TODO: When you sleep you enter a dream, you have to get out of.
    // TODO: You can watch television. DONE!!!
    // TODO: Die dream = die IRL

    public class HouseFlags {
        String[] amongUsPrompts = {
                "You watch a show about the life of an imposter.\n" +
                        "The show has a phenomenal deep meaning and it lasts 45 minutes before ending.",
                "An excited announcer explains the changes and additions in the latest among us update.\n" +
                        "Looks like you just wasted 15 minutes of your life.",
                "'The ultimate guide to not being sus' shows up on screen.\n" +
                        "You learn so much about how to not be sus!"
        };
        String[] historyPrompts = {
                "You watch a fascinating documentary film about the rise of the internet.",
                "You cry to the end of a dramatic movie on the very early beginning of WW2.",
                "You watch a show about the secret meanings of the bible expressions."
        };
        String[] sportsPrompts = {"You close TV immediately. Watching sports is boring!"};
        public String[] channels = {"Among Us", "History", "Sports"};
        public boolean fbi, couch = false;
        public int[] channelCounters = new int[channels.length];
        public String[][] tvPrompts = {amongUsPrompts, historyPrompts, sportsPrompts};

        public HouseFlags(){
            for(int i = 0; i < channelCounters.length; i++){
                channelCounters[i] = -1;
            }
        }
    }

    public Room outside, garden, livingRoom, kitchen, guestRoom;
    public Room hallway, bedroom, bathroom, balcony;
    public Interactive laptop, painting, bed, trampoline, tv, couch, guestCouch;
    HouseFlags flags = new HouseFlags();

    public Room onBedActivation(){
        System.out.println("You enter the bed and close your eyes.\n" +
                "You lose your consciousness and find yourself in a dream.");
        System.out.println("Unfortunately, this section of the game hasn't been written yet!");
        return new Room("");
    }

    public void onPaintingActivation(){
        System.out.println("Your slide the painting to the side, and reveal a small hole in the wall," +
                "approximately the size of a book.\n" +
                "This is a safe place to store your killing tools. No one is supposed to find them here.\n");
    }

    public void onCouchActivation(boolean outEvent){
        if(flags.couch){
            System.out.println("You get up from the couch.");
            flags.couch = false;
        }
        else if(!outEvent){
            System.out.println("You sit on the couch.");
            flags.couch = true;
        }
    }
    public void onCouchActivation(){
        onCouchActivation(false);
    }
    public void onTvActivation(){
        onTvActivation(false);
    }
    public void onTvActivation(Boolean alreadyOn){
        if(alreadyOn){
            System.out.println("The television is already on.");
        }
        else{
            System.out.println("You turn on the television.");
        }




        // Checking if a shuffle is needed
        for(int i = 0; i < flags.channelCounters.length; i++){
            if(flags.channelCounters[i] == flags.tvPrompts[i].length || flags.channelCounters[i] == -1){
                flags.tvPrompts[i] = Utils.shuffleArr(flags.tvPrompts[i]);
                flags.channelCounters[i] = 0;
            }
        }
        String[] options = {"Among us channel", "History channel", "Sports channel", "Turn TV off"};
        String title = "What will you watch?";
        int[] excludes = {3};
        int choice = Utils.menu(title, options);
        int index;
        if (choice != options.length){
            index = choice - 1;
            String prompt = flags.tvPrompts[index][flags.channelCounters[index]];
            System.out.println("You open " + options[index] + " and start watching.");
            System.out.println(prompt + "\n");
            flags.channelCounters[index]++;
            if(!Utils.contains(excludes, choice)) {
                onTvActivation(true);
            }
        }
    }
    public void onLaptopActivation(){
        System.out.println("You boot up the laptop and get it up and running.");
        String[] options = {"Go shopping on Ebay", "Hack into the FBI", "Turn laptop off"};
        int choice = -1;
        while (choice != options.length){
            if(flags.fbi){
                options[1] = "Hack into the FBI (again)";
            }
            choice = Utils.menu("What do you want to do now?", options);
            switch (choice){
                case 1:
                    System.out.println("Ebay is currently down. Sorry!");
                    break;
                case 2:
                    if(!flags.fbi){
                        System.out.println("You spend several hours hacking into the FBI.\n" +
                                "You do it! You download some data from their database to your computer.\n" +
                                "You suddenly hear an aggressive knock on the house front door.\n" +
                                "Wonder who it might be?");
                    }
                    else{
                        System.out.println("You hack into the FBI again, but this time you already know how to do it.\n" +
                                "It takes you much shorter period of time.");
                    }
                    flags.fbi = true;
                    break;
            }
            System.out.println("\nYou turn the laptop off.");
        }
    }
    public void onHouseLeave(){
        if(flags.fbi){
            System.out.println("As soon as you open the front door you see a group armed men in black.\n" +
                    "They wear bulletproof FBI suits, and each one of them is holding a gun.\n" +
                    "You understand that hacking into the FBI wasn't the best idea ever.");
            System.out.println("Game over!");
            System.exit(0);
        }
    }
    public Room onTrampolineActivation(){
        int rand = Utils.randInt(1, 5);
        if(rand == 1){
            System.out.println("You jump so hard that you REACH THE BALCONY IN THE SECOND FLOOR!\n" +
                    "You launch in the air and fall to the floor of the balcony.\n");
            return balcony;

        }
        else{
            System.out.println("You jump on the trampoline long enough until you are satisfied.");
        }
        return new Room("");
    }
}

public class Demo {
    static House house = new House();

    public static Room houseDemo(){
        // Outside the house
        house.outside = new Room("You stand outside your house.\n" +
                "What are you waiting for? Enter the house.");

        // House first floor
        house.garden = new Room("The garden is breathtaking, with green grass and flowers all around.\n" +
                "A huge trampoline is in the center of the garden, waiting to be jumped on.\n" +
                "You can enter back into the house.");
        house.livingRoom = new Room("You are at the house living room. \n" +
                "A couch stands in front of a coffee table and a 50 inch television.\n" +
                "You can enter the kitchen, the guest room and the garden.\n" +
                "You can also take the stairs and go up to the second floor, or leave the house and go out.");
        house.kitchen = new Room("The kitchen is accessorized with a fridge, oven with stove and a sink with some dirty dishes.\n" +
                "There is also a dining table you can eat on.\n" +
                "You can go out to the living room.");
        house.guestRoom = new Room("This is a usual guest room - with a couch, a bed and a mini bar.\n" +
                "When you have guests overnight they stay here.\n" +
                "You can go out to the living room.");

        // House second floor
        house.hallway = new Room("You are at the hallway of the house second floor.\n" +
                "You can enter the bedroom, bathroom and balcony, or take the stairs and go down the first floor.");
        house.bedroom = new Room("You are at your bedroom.\n" +
                "You see a bed, a desk and a painting hanged on the wall.\n" +
                "On the desk sits your laptop, a silver, premium Lenovo model.\n" +
                "You can go out to the hallway.");
        house.bathroom = new Room("You are at the bathroom.\n" +
                "A toilet and a shower stand close to each other.\n" +
                "You can go out to the hallway");
        house.balcony = new Room("You stand on the balcony.\n" +
                "A coffee table and two chairs are turning to a nice view of the garden.\n" +
                "You can enter back to the hallway.");

        // Wiring up locations
        house.outside.addLocation(Room.LocationMode.map, "house", house.livingRoom,
                "You unlock the house front door and walk into the living room.");
        house.outside.addLocation(Room.LocationMode.compass, "in", house.livingRoom,
                "You unlock the house front door and walk into the living room.");
        house.livingRoom.addLocation(Room.LocationMode.map, "garden", house.garden,
                "You open a light glass door which leads to the garden.");
        house.livingRoom.addLocation(Room.LocationMode.map, "kitchen", house.kitchen,
                "You enter the kitchen.");
        house.livingRoom.addLocation(Room.LocationMode.map, "guest room", house.guestRoom,
                "You open the door and step into the guest room.");
        house.livingRoom.addLocation(Room.LocationMode.compass, "up", house.hallway,
                "You climb the grand staircase and reach the hallway of the second floor.");
        house.livingRoom.addLocation(Room.LocationMode.compass, "out", house.outside,
                "You leave the house and close the door behind you.");
        house.garden.addLocation(Room.LocationMode.map, "house", house.livingRoom,
                "You leave the garden and enter the living room.");
        house.garden.addLocation(Room.LocationMode.compass, "in", house.livingRoom,
                "You leave the garden and enter the living room.");
        house.hallway.addLocation(Room.LocationMode.compass, "down", house.livingRoom,
                "You climb down the staircase and step into the living room, reaching the ground floor.");
        house.hallway.addLocation(Room.LocationMode.map, "bedroom", house.bedroom, "You open your bedroom door and walk in.");
        house.hallway.addLocation(Room.LocationMode.map, "bathroom", house.bathroom, "You walk into the bathroom.");
        house.hallway.addLocation(Room.LocationMode.map, "balcony", house.balcony,
                "You step towards the end of the hallway and go out to the balcony.");
        house.hallway.addLocation(Room.LocationMode.compass, "out", house.balcony,
                "You step towards the end of the hallway and go out to the balcony.");
        house.bedroom.addLocation(Room.LocationMode.compass, "out", house.hallway, "You open the bedroom door and get out to the hallway.");
        house.bathroom.addLocation(Room.LocationMode.compass, "out", house.hallway, "You open the bathroom door and leave to the hallway.");
        house.guestRoom.addLocation(Room.LocationMode.compass, "out", house.livingRoom,
                "You leave the guest room and return to the living room.");
        house.kitchen.addLocation(Room.LocationMode.compass, "out", house.livingRoom,
                "You leave the kitchen and return to the living room.");
        house.balcony.addLocation(Room.LocationMode.compass, "down", house.garden,
                "You decide to jump 3 meters down just for fun.\nYou find yourself in the garden and your body is on fire.");
        house.balcony.addLocation(Room.LocationMode.compass, "in", house.hallway,
                "You enter back into the house and find yourself in the hallway of the second floor.");
        house.balcony.addLocation(Room.LocationMode.map, "house", house.hallway,
                "The warm sun starts to annoy you and you go back to the second floor hallway.");

        // Making interactives
        house.laptop = new Interactive("laptop",
                "This is a premium Lenovo model, with intel core i5 and a nice 15.4 inch display.");
        house.painting = new Interactive("painting",
                "This is a painting of a fantasy dragon and a brave knight in an epic battle.");
        house.bed = new Interactive("bed",
                "This is your bed. It is covered with among us bed sheet and blanket.");
        house.trampoline = new Interactive("trampoline",
                "This is a trampoline. You can jump on it.");
        house.tv = new Interactive("tv",
                "This is a flat, OLED 4k 50\" television made by Samsung Ltd.");
        house.couch = new Interactive("couch",
                "This is a cozy and comfortable couch. Perfect for watching tv!");
        house.guestCouch = new Interactive("couch",
                "This is the guest couch. The sofa in the living room is much better!");

        // Binding interactive events
        house.laptop.addEvent(Interactive.EventType.activation, "activateLaptop");
        house.bed.addEvent(Interactive.EventType.activation, "activateBed");
        house.trampoline.addEvent(Interactive.EventType.activation, "activateTrampoline");
        house.tv.addEvent(Interactive.EventType.activation, "activateTv");
        house.couch.addEvent(Interactive.EventType.activation, "activateCouch");
        house.guestCouch.addEvent(Interactive.EventType.activation, "activateCouch");
        house.painting.addEvent(Interactive.EventType.activation, "activatePainting");

        // Adding interactives to matching rooms
        house.bedroom.addInteractive(house.bed);
        house.bedroom.addInteractive(house.laptop);
        house.bedroom.addInteractive(house.painting);
        house.garden.addInteractive(house.trampoline);
        house.livingRoom.addInteractive(house.tv);
        house.livingRoom.addInteractive(house.couch);
        house.guestRoom.addInteractive(house.guestCouch);

        // Adding room events
        house.outside.addEvent(Room.EventType.roomEnter, "houseLeave");
        house.livingRoom.addEvent(Room.EventType.roomLeave, "couchRoomLeave");
        house.guestRoom.addEvent(Room.EventType.roomLeave, "couchRoomLeave");

        // Returning the starting room
        return house.outside;
    }
    public static Room eventHandler(String eventName){
        switch (eventName){
            case "activateLaptop":
                house.onLaptopActivation();
                break;
            case "activateTrampoline":
                return house.onTrampolineActivation();
            case "activatePainting":
                house.onPaintingActivation();
                break;
            case "activateBed":
                return house.onBedActivation();
            case "activateTv":
                house.onTvActivation();
                break;
            case "activateCouch":
                house.onCouchActivation();
            case "houseLeave":
                house.onHouseLeave();
                break;
            case "couchRoomLeave":
                house.onCouchActivation(true);
                break;
        }
        return new Room("");
    }
}
