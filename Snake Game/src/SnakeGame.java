import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

public class SnakeGame extends JPanel implements ActionListener, KeyListener{ 

    private class  Tile //created it private so that only the SnakeGame can access the class
    {
        int x;
        int y;

        Tile(int x, int y) { //created a constructor
            this.x = x;
            this.y = y;
        }
    }
    
    int boardWidth;
    int boardHeight;
    int tileSize = 25; //each tile in the game is 25x25 pixels

    //Snake
    Tile snakeHead; // will use the private class Tile to create the snakeHead
    ArrayList<Tile> snakeBody = new ArrayList<Tile>(); //created an arraylist of tiles to create the snakeBody

    //Food

    Tile food;
    Random random; //created a random object

    //game logic
    Timer gameLoop; //created a timer object
    int VelocityX;
    int VelocityY;
    boolean gameOver = false;


    SnakeGame(int boardWidth, int boardHeight) { //constructor
        this.boardWidth = boardWidth;
        this.boardHeight = boardHeight;
        setPreferredSize(new Dimension(this.boardWidth, this.boardHeight));
        setBackground(Color.black);
        addKeyListener(this); //makes the SnakeGame listen to the keyboard
        setFocusable(true);
        

        snakeHead = new Tile(5, 5); //default starting position of the snakeHead
        snakeBody = new ArrayList<Tile>(); //created an arraylist of tiles to create the snakeBody

        food = new Tile(10,10); //default starting position of the food
        random = new Random(); //created a random object
        placeFood(); //created a function to randomly place the food somewhere on the field
        
        gameLoop = new Timer (100, this); //every 100 miliseconds the timer will call the actionPerformed function
        gameLoop.start(); //starts the timer

        VelocityX = 0;
        VelocityY = 0; //going downwards
	}	
    
    public boolean collision(Tile tile1, Tile tile2)
    {
        return tile1.x == tile2.x && tile1.y == tile2.y; //if the x and y position of the two tiles are the same, then there is a collision
    }

    public void paintComponent(Graphics g) //created a function to draw the snake, g is used for drawing
    {
        super.paintComponent(g);
        draw(g); //created a function to draw the snake
    }

    public void draw(Graphics g)
    {
        //snake
        g.setColor(Color.green); //sets color of the pen to green
        g.fillRect(snakeHead.x * tileSize, snakeHead.y * tileSize, tileSize, tileSize); //we draw a rectangle at the position of the snakeHead
        // we multiplied the position of the snakeHead being 5 by 5 by the tileSize of 25 to get the position of the snakeHead in pixels being that each box is 25x25 pixels

        //food
        g.setColor(Color.red);
        g.fillRect(food.x * tileSize, food.y * tileSize, tileSize, tileSize); //we draw a rectangle at the position of the snakeHead

        //snakeBody: draw each body part of the snake in the arraylist
        for(int i = 0; i< snakeBody.size(); i++)
        {
            Tile snakePart = snakeBody.get(i); //gets the tile at the index i
            g.setColor(Color.green); //sets color of the pen to green
            g.fillRect(snakePart.x * tileSize, snakePart.y * tileSize, tileSize, tileSize); //we draw a rectangle at the position of the snakeHead

        }

        //score
        g.setFont(new Font("Arial", Font.BOLD, 30)); //sets the font of the score
        if(gameOver)
        {
            g.setColor(Color.red);
            g.drawString("Game Over", boardWidth / 2 - 100, boardHeight / 2); //draws the score at the center of the screen
        }
        else
        {
            g.setColor(Color.white);
            g.drawString("Score: " + snakeBody.size(), 10, 30); //draws the score at the top left corner of the screen (10, 30
        }
    }

    //create a function to randomly place the food somewhere on the field
    public void placeFood()
    {
        food.x = random.nextInt(boardWidth / tileSize); //randomly places the food on the x axis. 600 / 25 = 24. From 0 to 24 times 25 = 600 
        food.y = random.nextInt(boardHeight / tileSize); //randomly places the food on the x axis

    }

    public void move()
    {
        //adds a check for collision with food to eat it 
        if(collision(snakeHead, food))
        {
            snakeBody.add(new Tile(food.x, food.y)); //adds a new tile to the snakeBody
            placeFood(); //creates a new location for the food once it has been eaten by the snake
        }

        //snake body
        for(int i = snakeBody.size()-1; i >= 0; i--) //iterating backwards so that the snakeBody follows the snakeHead
        {
            Tile snakePart = snakeBody.get(i); //gets the tile of the arraylist, snakeBody, at the index i
            if(i == 0) //this means this the first tile in the snakeBody, the arraylist, that comes right after the head
            {
                snakePart.x = snakeHead.x; //sets the x position of the snakePart to the x position of the snakeHead
                snakePart.y = snakeHead.y; //sets the y position of the snakePart to the y position of the snakeHead
            }
            else //otherwise we want the next tile after the first tile that's already in the snakebody to copy its previous position 
            {
                Tile prevSnakePart = snakeBody.get(i-1); //gets the tile at the index i-1
                snakePart.x = prevSnakePart.x; //sets the x position of the snakePart to the x position of the prevSnakePart
                snakePart.y = prevSnakePart.y; //sets the y position of the snakePart to the y position of the prevSnakePart
            }
        }
        

        //snake head
        snakeHead.x += VelocityX; //we add the velocity to the x position of the snakeHead
        snakeHead.y += VelocityY; //we add the velocity to the y position of the snakeHead
        
        //game over conditions

        // if snake collides with its own body
        for(int i = 0; i < snakeBody.size(); i++)
        {
            Tile snakePart = snakeBody.get(i); //gets the tile at the index i
            if(collision(snakeHead, snakePart)) //if the snakeHead collides with the snakePart
            {
                gameOver = true;
            }
        }

        //if snake collides with the wall
        if(snakeHead.x * tileSize < 0 || snakeHead.x * tileSize > boardWidth)
        {
            gameOver = true;
        
        }
        if(snakeHead.y * tileSize < 0 || snakeHead.y * tileSize > boardHeight)
        {
            gameOver = true;
        
        }
    }
    @Override
    //create a function to move the snake
    public void actionPerformed(ActionEvent e)
    {
        move(); //we need to call  move function that updates the x and y function of the snake
        repaint(); //repaints the screen by calling draw over and over again to create the animation
        
        

        if(gameOver == true )
        {
             gameLoop.stop();
           //randomizes the position of the head for next game 
            int positionX = random.nextInt(boardWidth / tileSize); //randomly places the food on the x axis. 600 / 25 = 24. From 0 to 24 times 25 = 600 
            int positionY = random.nextInt(boardHeight / tileSize);
            snakeHead = new Tile(positionX, positionY); //default starting position of the snakeHead 
            

            //randomizes the position of the food for next game
            placeFood();

            //reset the velocity, so that it doesn't continue moving in the same direction gameOver was triggered
            VelocityX = 0;
            VelocityY = 0; 


            //asks the user if they want to play again
            int playAgain = JOptionPane.showConfirmDialog(null, "Game Over! Your score is: " + snakeBody.size() + "\nDo you want to play again?", "Game Over", JOptionPane.YES_NO_OPTION);
            if(playAgain == JOptionPane.YES_OPTION)
            {
            // calls the clear function to clear the snakeBody arraylist
            snakeBody.clear();
            gameLoop.start(); //starts the timer
                //resets the gameOver boolean to false and gamestart to true
            gameOver = false;
            }
            else
            {
                System.exit(0); //exits the program
            }
        
        }
    
    }

    public void clear() //clears the arraylist of the snakeBody
    {
         for(int i = 0; i < snakeBody.size(); i++)
            {
                snakeBody.remove(i); //removes all the tiles in the snakeBody
            } //removes all the tiles in the snakeBody
    }

     @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_UP && VelocityY != 1 ) //if the up arrow is pressed, -1 is upwards
        {
            VelocityX = 0;
            VelocityY = -1;
        }
        else if(e.getKeyCode() == KeyEvent.VK_DOWN && VelocityY != -1) //if the up arrow is pressed, -1 is upwards
        {
            VelocityX = 0;
            VelocityY = 1;
        }
        else if(e.getKeyCode() == KeyEvent.VK_RIGHT && VelocityX != -1) //if the up arrow is pressed, -1 is upwards
        {
            VelocityX = 1;
            VelocityY = 0;
        }
        else if(e.getKeyCode() == KeyEvent.VK_LEFT && VelocityX != 1) //if the up arrow is pressed, -1 is upwards
        {
            VelocityX = -1;
            VelocityY = 0;
        }
    
    }

//don't need these function but we need to define them is all 
    @Override
    public void keyTyped(KeyEvent e) {
    }


    @Override
    public void keyReleased(KeyEvent e) {
    }
    
}