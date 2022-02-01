import java.io.*;
import java.util.*;


class League {
    ArrayList<Team> teams = new ArrayList<>();
    public League() {
        String[] team_names = {"Manchester City", "Liverpool", "Chelsea", "Manchester United", "West Ham", "Arsenal", "Tottenham", "Wolves", "Brighton", "Leicester", "Aston Villa", "Southampton", "Crystal Palace", "Brentford", "Leeds United", "Everton", "Norwich", "Newcastle", "Watford", "Burnley"};
        for (String team: team_names) {
            this.teams.add(new Team(team));
        }
    }
    public void value_leaderboard() {
        for (Team team: teams) {
            System.out.println(team);
        }
    }

    public boolean choices() {
        Scanner input = new Scanner(System.in);
        System.out.println("[1] Team Values\n[2] Check team rosters\n[3] Exit");
        byte choice = 0;
        try {
            choice = input.nextByte();
            if (choice < 1 || choice > 3) {
                throw new InputMismatchException();
            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid number");
        }
        if (choice == 1) {
            value_leaderboard();
        }
        if (choice == 2) {
            System.out.println("Pick a team: ");
            int i = 1;
            for (Team item: this.teams) {
                System.out.println("[" + i + "]" + " " + item.name);
                i++;
            }
            System.out.print("\n");
            choice = input.nextByte();
            try {
                this.teams.get(choice - 1).roster();
            }
            catch (InputMismatchException e) {
                System.out.println("Invalid number");
            }
        }
        return choice == 3;
    }
}


class Team {
    String name;
    ArrayList<Player> players;
    int value;

    public Team(String team_name) {
        this.name = team_name;
        this.players = generate_roster();
        this.value = roster_worth(this.players);
    }

    private int roster_worth(ArrayList<Player> players) {
        int value = 0;
        for (Player player: players) {
            value += player.value;
        }
        return value;
    }

    public String toString() {
        return this.name + ", worth " + this.value + "M";
    }

    private ArrayList<Player> generate_roster() {
        Random random = new Random();
        ArrayList<Player> players = new ArrayList<>();
        for (int i = 0; i < 11; i++) {
            String position = null;
            if (i < 1) {
                position = "goalkeeper";
            }
            if (i >= 1 && i < 5) {
                position = "defender";
            }
            if (i >= 5 && i < 8) {
                position = "midfielder";
            }
            if (i >= 8) {
                position = "attacker";
            }
            players.add(new Player(generate_name(), random.nextInt(110)+10, position));
        }
        return players;
    }

    void roster() {
        System.out.println(this.name + ":");
        for (Player item : this.players) {
            System.out.println(item);
        }
    }

    private String generate_name() {
        File first = new File("first.txt");
        File second = new File("second.txt");
        Random random = new Random();
        RandomAccessFile f_name, s_name;
        String name = null;
        try {
            f_name = new RandomAccessFile(first, "r");
            s_name = new RandomAccessFile(second, "r");
            f_name.seek(random.nextLong(f_name.length()));
            s_name.seek(random.nextLong(s_name.length()));
            f_name.readLine();
            s_name.readLine();
            name = f_name.readLine() + " " + s_name.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return name;
    }
}


class Player {
    String name;
    String position;
    int value;
    public Player(String name_is, int value_is, String position_is) {
        this.name = name_is;
        this.value = value_is;
        this.position = position_is;
    }
    public String toString() {
        return this.name + " is a " + this.position + " and is worth £" + this.value + "M";
    }
}


class Main {
    public static void main(String[] args) {
        League league = new League();
        boolean done = false;
        while (!done) {
            done = league.choices();
        }
    }
}