package hammurabi.src.main.java;               // package declaration
import java.util.Random;         // imports go here
import java.util.Scanner;

public class Hammurabi {         // must save in a file named Hammurabi.java
    Random rand = new Random();  // this is an instance variable
    Scanner scanner = new Scanner(System.in);
    int population = 100;
    int bushelsOwned = 2800;
    int acresOwned = 1000;
    public int yield = 0;
    public int year = 0;
    public int landPrice;
    public int input; // temp
    public int peopleFed;
    public int howManyPeopleStarved = 0;
    public int immigrants = 0;
    public int eaten = 0;
    public int plagueDeaths = 0;





    public static void main(String[] args) { // required in every Java program
        new Hammurabi().playGame();
    }

    void playGame() {
        while(year <= 10) {
            year++;
            printReport();
            landPrice = newCostOfLand();
            determineBuySellNothing();
            askHowMuchGrainToFeedPeople();
            askHowManyAcresToPlant();
            howManyPeopleStarved = starvationDeaths(population,peopleFed * 20);
            if(uprising(population,howManyPeopleStarved)) inputError(1); //people ate you
            immigrants = immigrants(population,acresOwned,bushelsOwned);
            plagueDeaths = plagueDeaths(population);
            population = population - howManyPeopleStarved + immigrants - plagueDeaths;

            // declare local variables here: grain, population, etc.
            // statements go after the declations

        }
        System.out.println("Game over");

    }
    public void printReport(){
        String report =
                "This is your yearly report.\n" +
                "Year: " + year + "\n" +
                "People starved: " + howManyPeopleStarved + "\n" +
                "Immigration: " + immigrants + "\n" +
                "Population : " + population + "\n" +
                "Acres owned: " +  acresOwned + "\n" +
                "Harvest: " + yield + " per acre \n" +
                "Rats ate " + eaten + " bushels \n" +
                "Bushels owned: " + bushelsOwned + "\n" +
                "Deaths from plague: " + plagueDeaths + "\n";
        System.out.println(report);
    }
    public void determineBuySellNothing(){
        //landPrice = (int) (7 * Math.random() + 17); //17 - 23 range
        do{
            System.out.println("Buy/Sell Acres? Enter 0 for buy 1 for sell 2 for nothing");
            input = scanner.nextInt();
        }while(input != 0 && input!=1 && input !=2);
        if(input == 0) askHowManyAcresToBuy();
        else if(input == 1) askHowManyAcresToSell();
    }


    public void askHowManyAcresToBuy(){
        do {
            System.out.println("Land is trading at "+ landPrice + " per acre.");
            System.out.println("How many acres would you like to buy");
            input = scanner.nextInt();
            if(input * landPrice > bushelsOwned) System.out.println("Not enough bushels buddy.");
            else if (input < 0) inputError(0);
        }while(input * landPrice > bushelsOwned);
        acresOwned += input; //add the acres bought
        bushelsOwned -= input * landPrice; //subtract the bushels used to buy using rate
    }
    public void askHowManyAcresToSell(){
        do{
            System.out.println("Land is trading " + landPrice + " per acre.");
            System.out.println("How many acres would you like to sell?");
            input = scanner.nextInt();
            if(input > acresOwned) System.out.println("You don't own enough acres for that buddy. Try again");
             else if (input < 0) inputError(0);
        }
        while(input > acresOwned); //check negative
        acresOwned-= input;
        bushelsOwned+= input * landPrice;
    }


    public void askHowMuchGrainToFeedPeople(){
        do{
            System.out.println("Each person needs 20 bushels a year to survive.");
            System.out.println("How many bushels do you wish to feed your people?");
            input = scanner.nextInt();
            if(input > bushelsOwned) System.out.println("You don't have enough bushels for that buddy.");
            else if (input < 0) inputError(0);
        } while(input > bushelsOwned); //check negative
        bushelsOwned -= input;
        peopleFed = input /20;
    }

    public void askHowManyAcresToPlant(){
        do{
            System.out.println("Each person in your population can farm 10 acres. It takes 2 bushels to farm an acre.");
            System.out.println("How many acres do you wish to plant?");
            input = scanner.nextInt();
            if(input < 0) inputError(0);
            else if (input > acresOwned) System.out.println("You don't have enough acres for that buddy.");
            else if (input > bushelsOwned * 2) System.out.println("You don't have enough bushels my guy.");
            else if(input > population *  10) System.out.println("You don't have the manpower for that bozo.");
        }while(input < 0 || input > acresOwned || input /2 > bushelsOwned || input > population *  10);
        bushelsOwned-= input * 2;
        yield = (int) (Math.random() * 6 + 1); // 1 - 6 yield
        bushelsOwned+=harvest(input); //calls harvest method for input
    }

    public int plagueDeaths(int population){
        if((int) (Math.random() * 100 + 1) <= 15) return this.population = (int) (Math.ceil(population / 2));
        return 0;
    }
    public int starvationDeaths(int population, int bushelsFedToPeople){
        if(bushelsFedToPeople / 20  > population) return 0;
        return this.howManyPeopleStarved = population - (bushelsFedToPeople / 20);
    }
    public boolean uprising(int population, int howManyPeopleStarved){
        return population * .45 <= howManyPeopleStarved; // pass this into this
    }
    public int immigrants(int population, int acresOwned, int grainInStorage){
        if(howManyPeopleStarved > 0) return 0;
       return this.immigrants = (20* acresOwned + grainInStorage) / (100 * population) + 1; //grainInStorage = bushelsOwned
    }
//    public int harvest(int acres, int bushelsUsedAsSeed){
//        return 0;
//    }
    public int harvest(int acres){
        if(acres ==  0)return 0;
        return acres * (int) (Math.random() * 6 + 1);
    }
    public int grainEatenByRats(int bushels){

        return 0;
    }
    public int newCostOfLand(){
         return landPrice = (int) (7 * Math.random() + 17); //17 - 23 range
    }
    public void inputError(int errorCode){
        String errorReason = "";
        switch(errorCode){
            case 0: errorReason = "Think long and hard then try again.";
            break;
            case 1: errorReason = "You starved too many people so they ate you instead.";
            break;
        }
        System.out.println(errorReason);


    }

    //other methods go here
}
