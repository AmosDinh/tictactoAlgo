package tictactoeonly
import secondBot.Bot2
import java.util.Random
import java.util.Scanner
//counters
var countBvsB = 1//idicates if it is the first turn
var countBvsB1 = 1
var countBvsB2= 1
var countBvsB3 = 1
var countBvsB4 = 1
var countBreak= 0

var countMyRepeater= 0

//permanetn vars//
var scanner= Scanner(System.`in` )
var playerWin = 0
var botWin = 0
var drawCount= 0
var gamesLimit= 0 // how many games the 2 bots should play
var whoStarts = 0 //first indicates for gameMaker is multiplied by -1

//temp vars//
var theLayout = arrayListOf<String>("1","2","3","4","5","6","7","8","9")
var currentLayout = arrayListOf<String>()
var whoseTurn = whoStarts
var lastPlayerMove = 0
var Bot1 = Bot()
var Bot2 = Bot2()
var newStartStatus = true
var anzeigeBot1win = 0
var anzeigeBot2win = 0
var anzeigeDraw = 0
var cpercent = 0

var averagemovecount:Double = 0.0
/*
fun gameMakerBvsB(){

    countBvsB++
    if(countBvsB%50 ==0){
        countBvsB++
        return
    }

    Bot1.printall()
    println("Bot1: $botWin, Bot2: $playerWin, Draw: $drawCount, after ${botWin+ playerWin+ drawCount} matches")
    reset()

    if(whoStarts == 1){
        // Bot().gameBrain()

        // var (botMove,win)=Bot().checkWinOfBotFIX()
        var botMove= Bot1.addToTBotArray()
        println("this is b1 move $botMove")
        //  setLayout( "B1",botMove.toString())
        whoseTurn = 3
        Bot2.addToTPlayerArray(botMove)
    }else{
        //Bot2().gameBrain()

        var botMove= Bot2.addToTBotArray()
        println("this is b2 move $botMove")
        // setLayout( "B2",botMove.toString())
        whoseTurn = 2
        Bot1.addToTPlayerArray(botMove)
    }


    gameProcessBvsB()


}*/


fun gameProcessBvsB(doNewStart:Boolean){
    if(doNewStart){
        countBvsB++
        if( countBvsB%50==0){
            println("triggerrrrrrrrrrrrrrrrrr $countBvsB PPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPP")
            countBvsB++
            return

        }


       // Bot1.printall()
       // println("Bot1: $botWin, Bot2: $playerWin, Draw: $drawCount, after ${botWin+ playerWin+ drawCount} matches")
        reset()

        if(whoStarts == 1){
            // Bot().gameBrain()

            // var (botMove,win)=Bot().checkWinOfBotFIX()
            var botMove= Bot1.addToTBotArray()
           // println("this is b1 move $botMove")
            //  setLayout( "B1",botMove.toString())
            whoseTurn = 3
            Bot2.addToTPlayerArray(botMove)
        }else{
            //Bot2().gameBrain()

            var botMove= Bot2.addToTBotArray()
           // println("this is b2 move $botMove")
            // setLayout( "B2",botMove.toString())
            whoseTurn = 2
            Bot1.addToTPlayerArray(botMove)
        }




    }
    averagemovecount++

    var (_,win1)=Bot1.checkForWin()

    Bot2.checkForWin()
    when{
        win1 == 555 -> {
            playerWin++
            whoStarts*= -1
           newStartStatus= true

            return
        }
        win1 == 111 -> {
            drawCount++
            whoStarts*= -1
           newStartStatus = true

            return
        }
        win1 == 666-> {
            botWin++
            whoStarts *= -1
          newStartStatus= true

            return
        }
    }

    if(whoseTurn%2 == 1){
        // Bot2().gameBrain()

        var botMove= Bot2.addToTBotArray()
        // setLayout( "B2",botMove.toString())

        whoseTurn = 2
        Bot1.addToTPlayerArray(botMove)

    }else{
        if(whoseTurn%2 == 0) {
            // Bot().gameBrain()

            var botMove = Bot1.addToTBotArray()

            Bot2.addToTPlayerArray(botMove)
            // setLayout("B1",botMove.toString())
            whoseTurn = 3
        }
    }

}

fun gameMaker(){



    reset()
    println("Games: ${playerWin+ botWin + drawCount}")
    println("Win: B$botWin P$playerWin")
    println("Draws: $drawCount")

    if(whoStarts == 1){
        // Bot().gameBrain()

        // var (botMove,win)=Bot().checkWinOfBotFIX()
        var botMove=  Bot1.addToTBotArray()
        setLayout("BB",botMove.toString())
        println("Bot Started")
        whoseTurn = 3
        gameProcess()
    }else{
        println("You Start")
        var data = playerInput("nothing")
        println(data)
        /* if(currentLayout[data.toInt()-1].contains("B")||currentLayout[data.toInt()-1].contains("P")||data.length>1){
             println("Doesn't work")
             data = scanner.next()
         }*/
        setLayout( "PP",data)
        whoseTurn = 2
        Bot1.addToTPlayerArray(data.toInt())
        gameProcess()
    }



}

fun gameProcess(){



    var (botMove1,win1)=Bot1.checkForWin()
    Bot1.findError()



    when(true) {
        win1 == 555 -> {
            println("You win")
            playerWin++
            whoStarts*= -1
            gameMaker()

        }
        win1 == 111 -> {
            println("draw")
            drawCount++
            whoStarts*= -1
            gameMaker()

        }
        win1 == 666-> {
            println("Bot wins")
            botWin++
            whoStarts *= -1
            gameMaker()

        }

    }

    Bot().returnTheMaps() // <- prints the maps
    /* var (botMove2,win2)=Bot().checkWinOfBotFIX()
     println("error #9")
     if(win2 == 666){
         println("Bot wins")
         whoStarts*= -1
         botWin++
         gameMaker()
         return
     }*/ if(whoseTurn%2 == 1){
        println("Your Turn2")
        var data = playerInput("nothing")

        /* if((currentLayout[data.toInt()-1].contains("B"))||(currentLayout[data.toInt()-1].contains("P"))||(data.toInt()>9)){
             println("Doesn't work")
             data = scanner.next()
         }*/

        setLayout("PP",data)
        Bot1.addToTPlayerArray(data.toInt())
        whoseTurn = 2

    }else{
        //Bot().gameBrain()

        var botMove=  Bot1.addToTBotArray()
        setLayout( "BB",botMove.toString())

        whoseTurn = 3

    }
    gameProcess()
}
fun main(args: Array<String>) {

    println("How many games shall bots play against each other?")

    gamesLimit = checkInput("nothing").toInt()*2+2
    whoStarts = whoStartsDecider()

    Bot1.setStarted(whoStarts)
    Bot2.setStarted(whoStarts*-1)
    myRepeaterRepeater()
    bVsPinitializer()

}
fun checkInput(data:String):String {
   var dataTemp = data
    if(data=="nothing") dataTemp = scanner.next()
    if(dataTemp.toIntOrNull() == null|| dataTemp.toInt()<0){
        println("Doesn't work")
        dataTemp = scanner.next()
        checkInput(dataTemp)
    }
    return dataTemp
}
fun botVsBotinit(){


    // bVsPinitializer()

}
fun bVsPinitializer(){
    enemyIsPlayer = true
    Bot1.showWin()
    whoStarts = whoStartsDecider()
    println("Bot1: $botWin, Bot2: $playerWin, Draw: $drawCount, after ${botWin+ playerWin+ drawCount} matches")
    botWin = 0
    playerWin= 0
    drawCount = 0

    whoStarts = whoStartsDecider()
    Bot().setStarted(whoStarts)
    gameMaker()
}

fun whoStartsDecider():Int {

    var random = Random()
    var check =random.nextInt(3 - 1) + 1
    if(check ==2) return 1
    else return -1
}
fun setLayout( aMove: String,index: String){
    if(aMove != "0") {
        println("$index")
        currentLayout[index.toInt() - 1] = aMove
    }
    var a = currentLayout[0]
    var b = currentLayout[1]
    var c = currentLayout[2]
    var d = currentLayout[3]
    var e = currentLayout[4]
    var f = currentLayout[5]
    var g = currentLayout[6]
    var h = currentLayout[7]
    var i = currentLayout[8]

    println("  $a  $b  $c \n  $d  $e  $f \n  $g  $h  $i \n________________ ")

}

fun reset(){
    theLayout = arrayListOf<String>(" 1"," 2"," 3"," 4"," 5"," 6"," 7"," 8"," 9")
    currentLayout = arrayListOf<String>()
    whoseTurn = 0
    lastPlayerMove = 0

    currentLayout = theLayout

    //  setLayout("0","none")
}
fun myRepeaterRepeater(){

    if(countBvsB> gamesLimit)return



    countMyRepeater++
    myRepeater1()

    myRepeaterRepeater()

}
 fun myRepeater1(){

     if(countBvsB> gamesLimit)return
     if(countBvsB1%2==0){
         countBvsB1++
         return
     }

     countBvsB1++
     myRepeater2()
     myRepeater1()
 }
fun myRepeater2(){

    if(countBvsB> gamesLimit)return
    if(countBvsB2%2==0){
        countBvsB2++
        return
    }

    countBvsB2++
    myRepeater4()
    myRepeater1()
}

fun myRepeater4(){

    if(countBvsB> gamesLimit)return
    if(countBvsB4%2==0){
        countBvsB4++
        return
    }
    countBvsB4++
    myRepeater()
    myRepeater4()
}
fun myRepeater(){


    if(countBvsB> gamesLimit)return


    if(countBvsB%2==0){
        countBvsB++
        return
    }


    betw()
        myRepeater()



}
fun betw(){


    if(countBvsB>= gamesLimit){
        println("Bot1: $botWin, Bot2: $playerWin, Draw: $drawCount, after ${botWin+ playerWin+ drawCount} matches")
        return
    }
    if(newStartStatus== true){
        val x = botWin+ playerWin+ drawCount
        if(x%100==0){
            val apercent = botWin- anzeigeBot1win
            val bpercent = playerWin- anzeigeBot2win
            val averageMove = averagemovecount/100
             cpercent = drawCount - anzeigeDraw

            println(" $x matches played")
            println("Bot1: $apercent%, Bot2: $bpercent%, Draws: $cpercent%, average movecount: $averageMove")
            anzeigeBot1win = botWin
            anzeigeBot2win = playerWin
            anzeigeDraw = drawCount
            averagemovecount=0.0

        }
        countBvsB++
        newStartStatus = false
        gameProcessBvsB(true)

    }else gameProcessBvsB(false)

}
fun playerInput(data:String):String{
    var dataTemp = data
    if(dataTemp == "nothing")dataTemp = scanner.next()
    if ( dataTemp.toIntOrNull() == null||
            dataTemp.toInt()>9||
            dataTemp.toInt()<=0||
            currentLayout[dataTemp.toInt()-1].contains("B")||
            currentLayout[dataTemp.toInt()-1].contains("P")
           ) {
        println("Doesn't work")
         dataTemp = scanner.next()

        playerInput(dataTemp)
    }

    return dataTemp
}

