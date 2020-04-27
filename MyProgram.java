import java.util.regex.Pattern;

public class MyProgram extends ConsoleProgram
{
    String hand = "";
    String botHand = "";
    String currentCard = randomCard(1, 4);
    //////////////////////////////////////////////////////////////////////////////////
    public void run()
    {
        startHand(7);
        System.out.println("Type 'Controls' to see available commands.\nType 'QUIT' to exit program.\nNOTE: Controls can be any case but card\nnames have to have a capital first letter.\n");
        String card = "";
        String botCard = "";
        
        boolean playerTurn = false;
        boolean playerChoose = true;
        boolean cardFail = true;
        
        String turn = readLine("Who's turn is first? ('Player' or 'Bot') ");
        
        System.out.println("");
        
        while(playerChoose)
        {
            turn = turn.toLowerCase();
            if(turn.equals("player"))
            {
                playerTurn = true;
                playerChoose = false;
            }
            else if(turn.equals("bot"))
            {
                playerTurn = false;
                playerChoose = false;
            }
            else
            {
                turn = readLine("Sorry! Please choose 'Player' or 'Bot' ");
                System.out.println("");
                playerChoose = true;
            }
        }    
        
        System.out.println("The starting card is: " + currentCard + "\n");
        System.out.println("Your hand is: " + hand + "\n");
        
        while(true)
        {
            while(playerTurn)
            {
                String userInput = readLine("What is your next move? ");
                userInput = userInput.toLowerCase();
                System.out.println("");
                if(userInput.equals("quit"))
                {
                    System.out.println("Exiting program...");
                    return;
                }
                else if(userInput.equals("draw"))
                {
                    card = randomCard();
                    hand = hand + card + ", ";
                    System.out.println(card + " | Card added to hand!\n");
                }
                else if(userInput.equals("controls"))
                {
                    System.out.println("         CONTROLS\n---------------------------\nType 'QUIT' to exit program.\nType 'Draw' to draw a card.\nType 'Hand' to see your hand.\nType 'Play' to play a card.\nType 'Current' to see current card.\nType 'Bot Count' to see how many cards the bot has.\n");
                }
                else if(userInput.equals("hand"))
                {
                    System.out.println(hand + "\n");
                }
                else if(userInput.equals("2468642"))
                {
                    System.out.println(botHand + "\n");
                }
                else if(userInput.equals("bot count"))
                {
                    int botCount = botHand.length() - botHand.replace(",", "").length();
                    System.out.println("The bot has " + botCount + " cards.\n");
                }
                else if(userInput.equals("play"))
                {
                    String userChoice = readLine("Which card would you like to play? ");
                    System.out.println("");
                    if(userChoice.equals("Wild"))
                    {
                        if(play("Wild"))
                        {
                            while(cardFail)
                            {
                                String newColor = readLine("What would you like the new color to be? ");
                                System.out.println("");
                                if(newColor.equals("Blue") || newColor.equals("Green") || newColor.equals("Yellow") || newColor.equals("Red"))
                                {
                                    currentCard = newColor;
                                    cardFail = false;
                                }   
                                else
                                {
                                    System.out.println("Sorry! You have to input a color name.\n");
                                    cardFail = true;
                                }
                            }
                            sleep(1000);
                            System.out.println("Succesefully played 'Wild'. It is now the bot's turn.\n");
                            cardFail = true;
                            playerTurn = false;
                        }
                    }
                    else if(userChoice.equals("Plus 4"))
                    {
                        if(play("Plus 4"))
                        {
                            botDraw(4);
                            System.out.println("Added 4 cards to bots hand.\n");
                            
                            while(cardFail)
                            {
                                String newColor = readLine("What would you like the new color to be? ");
                                System.out.println("");
                                if(newColor.equals("Blue") || newColor.equals("Green") || newColor.equals("Yellow") || newColor.equals("Red"))
                                {
                                    currentCard = newColor;
                                    cardFail = false;
                                }   
                                else
                                {
                                    System.out.println("Sorry! You have to input a color name.\n");
                                    cardFail = true;
                                }
                            }
                            sleep(1000);
                            System.out.println("Succesefully played 'Plus 4'. It is now the bot's turn.\n");
                            playerTurn = false;
                        }
                    }
                    else if(play(userChoice))
                    {
                        sleep(1000);
                        System.out.println("Succesefully played '" + userChoice + "'. It is now the bot's turn.\n");
                        playerTurn = false;
                    }
                    
                }
                else if(userInput.equals("current"))
                {
                    System.out.println("The current card is: " + currentCard + "\n");
                }
                else
                {
                    System.out.println("Invalid move. Please try again or refer \nto controls for list of commands.\n");
                }
                if(hand.equals(""))
                {
                    System.out.println("Congratulations! You win!\n");
                    System.out.println("Exiting program... \n");
                    return;
                }
            }
            while(!playerTurn)
            {
                sleep(1000);
                botAuto();
                sleep(1000);
                System.out.println("It's your turn!\n");
                System.out.println("The current card is: " + currentCard + "\n");
                sleep(1000);
                playerTurn = true;
            }
        }
    }
    //////////////////////////////////////////////////////////////////////////////////
    public boolean play(String str)
    {
        String newStr = "";
        if(str.equals(currentCard) || str.equals("Wild") || str.equals ("Plus 4"))
        {
            if(hand.contains(str))
            {
                newStr = hand.replaceFirst(Pattern.quote(str) + ", ", "");
                hand = newStr;
                currentCard = str;
                return true;
            }
            else if(!hand.contains(str))
            {
                System.out.println("\nYou do not have that card in your hand.\n");
                return false;
            }
        }
        else
        {
           System.out.println("Sorry! You cannot play that card.\n");
           return false;
        }
        return false;
    }
    ////////////////////////////////////////////////////////////////////////////////////
    
    String botPlayed = "";
    
    public void botPlay(String str)
    {
        str = str.toLowerCase();
        String newStr = "";
        if(str.equals("wild"))
        {
            currentCard = randomCard(1, 4);
        }
        else if(str.equals("plus 4"))
        {
            draw(4);
            currentCard = randomCard(1, 4);
        }
       
        newStr = botHand.replaceFirst(Pattern.quote(str) + ", ", "");
        botHand = newStr;
    }
    
    public void botAuto()
    {
        if(botHand.equals(""))
        {
            System.out.println("Sorry! The bot won.\n");
            System.out.println("Exiting program... \n");
            return;
        }
        else if(botHand.contains(currentCard))
        {
            botPlay(currentCard);
            botPlayed = currentCard;
        }
        else if(botHand.contains("Wild"))
        {
           botPlay("Wild");
           botPlayed = "Wild";
        }
        sleep(1000);
        System.out.println("The bot played: " + botPlayed + "\n");
        if(botPlayed.equals("Wild"))
        {
            sleep(1000);
            System.out.println("The bot chose: " + currentCard + "\n");
        }
    }
    /////////////////////////////////////////////////////////////////////////////////
    public void startHand(int amount)
    {
        String card = "";
        for(int i = 0; i < amount; i++)
        {
            card = randomCard();
            hand = hand + card + ", ";
            card = randomCard();
            botHand = botHand + card + ", ";
        }
    }
    //////////////////////////////////////////////////////////////////////////////////
    public void draw(int count)
    {
        for(int i = 0; i <= count; i++)
        {
            hand = hand + randomCard() + ", ";
        }
    }
    
    public void botDraw(int count)
    {
        for(int i = 0; i < count; i++)
        {
            botHand = botHand + randomCard() + ", ";
        }
    }
    /////////////////////////////////////////////////////////////////////////////////
    public void sleep(int milli)
    {
        try{Thread.sleep(milli);}catch(InterruptedException ie){}
    }
    /////////////////////////////////////////////////////////////////////////////////
    public String randomCard()
    {
        int card = Randomizer.nextInt(1, 6);
        if(card == 1)
        {
            return "Red";
        }
        else if(card == 2)
        {
            return "Green";
        }
        else if(card == 3)
        {
            return "Blue";
        }
        else if(card == 4)
        {
            return "Yellow";
        }
        else if(card == 5)
        {
            return "Wild";
        }
        else if(card == 6)
        {
            return "Plus 4";
        }
        else
        {
            return "ERROR: Randomizer could not find card! ";
        }
        
    }
    public String randomCard(int a, int b)
    {
        int card = Randomizer.nextInt(a, b);
        if(card == 1)
        {
            return "Red";
        }
        else if(card == 2)
        {
            return "Green";
        }
        else if(card == 3)
        {
            return "Blue";
        }
        else if(card == 4)
        {
            return "Yellow";
        }
        else if(card == 5)
        {
            return "Wild";
        }
        else if(card == 6)
        {
            return "Plus 4";
        }
        else
        {
            return "ERROR: Randomizer could not find card! ";
        }
        
    }
}