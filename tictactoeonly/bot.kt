package tictactoeonly

import java.util.Random

var inheritCount = 0

data class Returnbot(var botMove  :Int, var won:Int)

data class ReturnTheMaps(var bmW:Map<Int,ArrayList<Int>>,var bmD:Map<Int,ArrayList<Int>>,var bmL:Map<Int,ArrayList<Int>>,
                         var pmW:Map<Int,ArrayList<Int>>,var pmD:Map<Int,ArrayList<Int>>,var pmL:Map<Int,ArrayList<Int>>)


var finalBotMove: Int = 0
var enemyIsPlayer = false
//the first root vars, store the info about multiple matches//
var botMapWIN = mutableMapOf<Int, ArrayList<Int>>()
var botMapLOSE = mutableMapOf<Int, ArrayList<Int>>()
var botMapDRAW  = mutableMapOf<Int, ArrayList<Int>>()

var playerMapWIN = mutableMapOf<Int, ArrayList<Int>>()
var playerMapLOSE = mutableMapOf<Int, ArrayList<Int>>()
var playerMapDRAW = mutableMapOf<Int, ArrayList<Int>>()
var tempTurnCount: Int = 1
var whoStarted: Int = 1 // 1 = bot, -1 = player


//third root vars, store for 1 turn only//
var tBotArray = arrayListOf<Int>()
var tPlayerArray = arrayListOf<Int>()


// win conditions, patterns//
var winList1 = listOf<Int>(1, 2, 3)
var winList2 = listOf<Int>(4, 5, 6)
var winList3 = listOf<Int>(7, 8, 9)
var winList4 = listOf<Int>(1, 4, 7)
var winList5 = listOf<Int>(2, 5, 8)
var winList6 = listOf<Int>(3, 6, 9)
var winList7 = listOf<Int>(1, 5, 9)
var winList8 = listOf<Int>(7, 5, 3)

var uncheckedList = mutableListOf<Int>(1, 2, 3, 4, 5, 6, 7, 8, 9)
//gameBrain vars //

//what bot should do//
//gamebrain vars
var BotMovesWIN = arrayListOf<Int>()

var willLoseArr = arrayListOf<Int>()
var willLoseRelPlayerArr = arrayListOf<Int>()
var willLoseRelPlayerLose = arrayListOf<Int>()

var doPlayersMoveArr = arrayListOf<Int>()
var doBotWINMoveArr =arrayListOf<Int>()
var doBotWINMoveRelPlayer = arrayListOf<Int>()
var doBotDRAWMoveArr =arrayListOf<Int>()
var doBotDRAWMoveRelPlayer = arrayListOf<Int>()

var showTheWIN = arrayListOf<Int>() // 1 = bot1,2 bot2,0= draw


//parse vars,so error with the botMapWIN etc doesnt  happen where it references to .clear() tBotArrays etc//



class Bot {




    private fun gameBrain() {




        willLoseArr.clear()
        willLoseRelPlayerArr.clear()
        willLoseRelPlayerLose.clear()

        //currently used :
        BotMovesWIN.clear()
        doPlayersMoveArr.clear()
        doBotWINMoveArr.clear()
        doBotWINMoveRelPlayer.clear()
        doBotDRAWMoveArr.clear()
        doBotDRAWMoveRelPlayer.clear()




        /*===================================================================================================*/
        //do move based on botWin and Draw (+ on botWIN/DRAW based on playerMapLOSE/DRAW)start//
        if(botMapWIN.isNotEmpty()){
            //based on botWIn


            botMapWIN.forEach { k, v -> if(k* whoStarted > 0 && v.size > tBotArray.size) {
                for (i in 0 until tBotArray.size) {
                    if (v[i] == tBotArray[i]) {
                        if (i == tBotArray.size - 1) doBotWINMoveArr.add(v[i+1])
                    }else break
                }
            }
            }

            //based on playerLOSE
            playerMapLOSE.forEach { k, v -> if(k * whoStarted > 0){
                if(whoStarted>0 && botMapWIN[k]!!.size > tPlayerArray.size){
                    for(i in 0 until tPlayerArray.size){
                        if(v[i]== tPlayerArray[i]){
                            if(i == tPlayerArray.size-1) doBotWINMoveRelPlayer.add(botMapWIN[k]!![i+1])
                        }else break
                    }
                }else if(whoStarted<0 && botMapWIN[k]!!.size >= tPlayerArray.size){
                    for(i in 0 until tPlayerArray.size){
                        if(v[i]== tPlayerArray[i]){
                            if(i == tPlayerArray.size-1) doBotWINMoveRelPlayer.add(botMapWIN[k]!![i])
                        }else break
                    }
                }
            }
            }
        }

        /*--------------------------------------------------------------------------------------------------------------*/
        if(botMapDRAW.isNotEmpty()){
          //based on botDRA

            botMapDRAW.forEach { k, v -> if(k* whoStarted > 0 && v.size > tBotArray.size) {
                for (i in 0 until tBotArray.size) {
                    if (v[i] == tBotArray[i]) {
                        if (i == tBotArray.size - 1) doBotDRAWMoveArr.add(v[i+1])
                    }else break
                }
            }
            }

            //based on playerDRAW
            playerMapDRAW.forEach { k, v -> if(k * whoStarted > 0){
                if(whoStarted>0 && botMapDRAW[k]!!.size > tPlayerArray.size){
                    for(i in 0 until tPlayerArray.size){
                        if(v[i]== tPlayerArray[i]){
                            if(i == tPlayerArray.size-1) doBotDRAWMoveRelPlayer.add(botMapDRAW[k]!![i+1])
                        }else break
                    }
                }else if(whoStarted<0 && botMapDRAW[k]!!.size >= tPlayerArray.size){
                    for(i in 0 until tPlayerArray.size){
                        if(v[i]== tPlayerArray[i]){
                            if(i == tPlayerArray.size-1) doBotDRAWMoveRelPlayer.add(botMapDRAW[k]!![i])
                        }else break
                    }
                }
            }
            }
        }

        if(enemyIsPlayer){
            println("doBotWINMove: $doBotWINMoveArr")
            println(" doBotWINMoverelP: $doBotWINMoveRelPlayer")
            println("doBotDRAWMOve: $doBotDRAWMoveArr")
            println(" doBotDRAWMOverelP: $doBotDRAWMoveRelPlayer")
        }

        /*===================================================================================================*/
        //do move based on botWin and Draw end//
        //do players move start//
        if(botMapLOSE.isNotEmpty()){
            playerMapWIN.forEach { k, v -> if(/*k* whoStarted>0&&*/ v.size> tPlayerArray.size) {
                for (i in 0 until tPlayerArray.size){
                    if(v[i]== tPlayerArray[i]){
                        if(i == tPlayerArray.size-1) doPlayersMoveArr.add(v[i+1])

                    } else break
                 }
            }
            }
        }
        if(botMapDRAW.isNotEmpty()){
            playerMapDRAW.forEach { k, v -> if(/*k* whoStarted>0&& */v.size> tPlayerArray.size) {
                for (i in 0 until tPlayerArray.size){
                    if(v[i]== tPlayerArray[i]){
                        if(i == tPlayerArray.size-1) doPlayersMoveArr.add(v[i+1])
                    }else break
                }
            }
            }
        }
        /*===================================================================================================*/
        if(botMapLOSE.isNotEmpty()){
            botMapLOSE.forEach { k, v -> if(k* whoStarted > 0 && v.size > tBotArray.size) {
                for (i in 0 until tBotArray.size) {
                    if (v[i] == tBotArray[i]) {
                        if (i == tBotArray.size - 1) willLoseArr.add(v[i+1])
                    }else break
                }
            }
            }

            //based on playerLOSE
            playerMapWIN.forEach { k, v -> if(k * whoStarted > 0){
                if(whoStarted>0 && botMapLOSE[k]!!.size > tPlayerArray.size){
                    for(i in 0 until tPlayerArray.size){
                        if(v[i]== tPlayerArray[i]){
                            if(i == tPlayerArray.size-1) willLoseRelPlayerArr.add(botMapLOSE[k]!![i+1])
                        }else break
                    }
                }else if(whoStarted<0 && botMapLOSE[k]!!.size >= tPlayerArray.size){
                    for(i in 0 until tPlayerArray.size){
                        if(v[i]== tPlayerArray[i]){
                            if(i == tPlayerArray.size-1) willLoseRelPlayerArr.add(botMapLOSE[k]!![i])
                        }else break
                    }
                }
            }
            }
        }
        //based on player lose -> don't do move player did when he lost
        if(botMapWIN.isNotEmpty()){
            playerMapLOSE.forEach{k,v -> if(k * whoStarted > 0&& v.size > tPlayerArray.size){
                for(i in 0 until tPlayerArray.size){
                    if(v[i]== tPlayerArray[i]){
                        if(i == tPlayerArray.size-1) willLoseRelPlayerLose.add(v[i+1])
                    }else break
                }
            }

            }
        }
        /*===================================================================================================*/

        if(enemyIsPlayer)println("doplayersmovearr $doPlayersMoveArr")
        //do players move end//

        /*evaluating the arrays, currently:
        * doPlayersMoveArr
        *
        * doBotWINMove
        * doBotWINMoverelPlayer
        * doBotDRAWMove
        * doBotDRAWMoverelPlayer
        *
        * willLoseArr
        * willLoseRelPlayerArr
        *
        *
        * */

        var only2DoPlayersMoveArr = arrayListOf<Int>()
        var only2DoBotWINMove = arrayListOf<Int>()
        var only2DoBotDRAWMove = arrayListOf<Int>()
        val allOnly2 = ArrayList<Int>()

        var only2willLose = arrayListOf<Int>()
        //win section start
        if(doPlayersMoveArr.isNotEmpty()) only2DoPlayersMoveArr = twoMostCommon(doPlayersMoveArr)
        when{
            doBotWINMoveArr.isNotEmpty()-> only2DoBotWINMove  = twoMostCommon(doBotWINMoveArr, doBotWINMoveRelPlayer)
            doBotWINMoveRelPlayer.isNotEmpty() -> only2DoBotWINMove  = twoMostCommon(doBotWINMoveRelPlayer, doBotWINMoveArr)

        }
        when{
            doBotDRAWMoveArr.isNotEmpty() -> only2DoBotDRAWMove = twoMostCommon(doBotDRAWMoveArr, doBotDRAWMoveRelPlayer)
            doBotDRAWMoveRelPlayer.isNotEmpty()-> only2DoBotDRAWMove = twoMostCommon(doBotDRAWMoveArr, doBotDRAWMoveArr)
        }

        only2DoPlayersMoveArr.forEach { allOnly2.add(it) }
        only2DoBotWINMove.forEach { allOnly2.add(it) }
        only2DoBotDRAWMove.forEach { allOnly2.add(it) }
        //win section end
        //lose section start

        when{
            willLoseArr.isNotEmpty() -> only2willLose = twoMostCommonWillLose(willLoseArr, willLoseRelPlayerArr, willLoseRelPlayerLose)
            willLoseRelPlayerArr.isNotEmpty() -> only2willLose = twoMostCommonWillLose( willLoseRelPlayerArr, willLoseRelPlayerLose, willLoseArr)
            willLoseRelPlayerLose.isNotEmpty() -> only2willLose = twoMostCommonWillLose(willLoseRelPlayerLose,willLoseArr, willLoseRelPlayerArr )
        }

        //lose section end
        //remove only2willLose from allOnly2 start
        var finalOnly2 = arrayListOf<Int>()
        var cloneOnly2 = allOnly2.clone()

/*
        if(allOnly2.isNotEmpty()&&only2willLose.isNotEmpty()) {
            finalOnly2 = allOnly2.clone() as ArrayList<Int>
            if(only2willLose.size>1){
                finalOnly2.remove(only2willLose.first())
                finalOnly2.remove(only2willLose.last())
            }else if(only2willLose.isNotEmpty())finalOnly2.remove(only2willLose.first())

        }else if(allOnly2.isEmpty()&&only2willLose.isNotEmpty()){
            only2willLose.forEach { v ->uncheckedList.forEach { v2 ->
                if(v != v2)finalOnly2.add(v2)
            }
            }
        }else if(only2willLose.isEmpty()){
            finalOnly2 = allOnly2.clone() as ArrayList<Int>

        }
*/



if(only2willLose.size ==2 ) {
    for (i in 0 until allOnly2.size) {
        if (allOnly2[i] != only2willLose[0] && allOnly2[i] != only2willLose[1]) finalOnly2.add(allOnly2[i])
    }
}else if(only2willLose.size == 1){
    for (i in 0 until allOnly2.size) {
        if (allOnly2[i] != only2willLose[0]) finalOnly2.add(allOnly2[i])
    }
}

        /*
if(allOnly2.isNotEmpty()) {
    when{
        only2willLose.size == 2 ->{
            finalOnly2 = allOnly2.clone() as ArrayList<Int>
            finalOnly2.remove(only2willLose[0])
            finalOnly2.remove(only2willLose[1])
        }
        only2willLose.size == 1 -> {
            finalOnly2 = allOnly2.clone() as ArrayList<Int>
            finalOnly2.remove(only2willLose[0])
            finalOnly2.remove(only2willLose[0])
        }
        else -> finalOnly2 =  allOnly2.clone() as ArrayList<Int>
    }
}else{
    when {
        only2willLose.size == 2 -> {
            uncheckedList.forEach {finalOnly2.add(it) }
            finalOnly2.remove(only2willLose[0])
            finalOnly2.remove(only2willLose[1])
        }
        only2willLose.size == 1 -> {
            uncheckedList.forEach {finalOnly2.add(it) }
            finalOnly2.remove(only2willLose[0])
        }
    }
}
*/
//remove only2willLose from allOnly2 end


BotMovesWIN.add(oneMostCommon(finalOnly2))
if(enemyIsPlayer){
    println("allOnly2: $allOnly2 \n" +
            "willLose: $willLoseArr \n " +
            "relPlayer: $willLoseRelPlayerArr \n " +
            "only2willLOSE: $only2willLose\n" +
            "finalOnly2: $finalOnly2\n" +
            "BotMovesWIN: $BotMovesWIN")
}
}


private fun oneMostCommon(tpassedArr1 :ArrayList<Int>):Int{
val random = Random()
    var passedArr1 = tpassedArr1.shuffled()
if(enemyIsPlayer)println("passedarraysze ${passedArr1.size}")


// var index = 0
// if(passedArr1.size > 0)index = random.nextInt(passedArr1.size)

val tempArr = ArrayList<Int>()
var tempArr2 = ArrayList<Int>()
//    var result = passedArr1[index]
var result = random.nextInt(9)
for( i in 0 until passedArr1.size){
    tempArr.clear()
    for(j in 0 until passedArr1.size){
        if(passedArr1[i] == passedArr1[j])tempArr.add(passedArr1[j])
        //if(enemyIsPlayer) println("added ${passedArr1[j]}")
    }
    if(tempArr2.isEmpty())tempArr2 = tempArr.clone() as ArrayList<Int>
    else if(tempArr2.size<tempArr.size) tempArr2 = tempArr.clone() as ArrayList<Int>
}
if(tempArr2.isNotEmpty())result = tempArr2[0]
if(enemyIsPlayer){
    println("this is result: $result, this is tempArr2: $tempArr2")
}
return result
}

private fun twoMostCommon(passedArr1 :ArrayList<Int>,passedArr2: ArrayList<Int> = ArrayList()):ArrayList<Int>{
val arr1 = ArrayList<Int>()
var tarr1 = ArrayList<Int>()
val tempArr  = ArrayList<Int>()
var result = ArrayList<Int>()
var result2 = ArrayList<Int>()
val only2numresult = ArrayList<Int>()
tarr1 = passedArr1.clone() as ArrayList<Int>
passedArr2.forEach { tarr1.add(it) }
//arr1 = tarr1.clone() as ArrayList<Int>
tarr1.forEach { if(uncheckedList.contains(it))arr1.add(it)}
for(i in 0 until arr1.size){
    tempArr.clear()
    for(j in 0 until arr1.size) {
        if (arr1[i]== arr1[j])tempArr.add(arr1[i])
    }
    if(result.isEmpty())result = tempArr.clone() as ArrayList<Int>
    else if (result.size < tempArr.size) result = tempArr.clone() as ArrayList<Int>
}

for(i in 0 until arr1.size){
    tempArr.clear()
    for(j in 0 until arr1.size) {
        if (arr1[i]== arr1[j]&&arr1[i]!= result[0])tempArr.add(arr1[i])
    }
    if(result2.isEmpty())result2 = tempArr.clone() as ArrayList<Int>
    else if (result2.size < tempArr.size) result2 = tempArr.clone() as ArrayList<Int>
}
result2.forEach { v->result.add(v) }
if(enemyIsPlayer) println("Result: $result, tempArr: $tempArr, tArr1 : $tarr1")
result.forEach { if(!only2numresult.contains(it)) only2numresult.add(it) }
return only2numresult
}
/*!!!differenc to the one above is removed if uncheckedlist contains it!!!*/
private fun twoMostCommonWillLose(passedArr1 :ArrayList<Int>,
                              passedArr2: ArrayList<Int> = ArrayList(),
                              passedArr3: ArrayList<Int> = ArrayList()):ArrayList<Int>{
var arr1 = ArrayList<Int>()

val tempArr  = ArrayList<Int>()
var result = ArrayList<Int>()
var result2 = ArrayList<Int>()
val only2numresult = ArrayList<Int>()
arr1 = passedArr1.clone() as ArrayList<Int>
passedArr2.forEach {  arr1.add(it) }
passedArr3.forEach {  arr1.add(it) }

for(i in 0 until arr1.size){
    tempArr.clear()
    for(j in 0 until arr1.size) {
        if (arr1[i]== arr1[j])tempArr.add(arr1[i])
    }
    if(result.isEmpty())result = tempArr.clone() as ArrayList<Int>
    else if (result.size < tempArr.size) result = tempArr.clone() as ArrayList<Int>
}

for(i in 0 until arr1.size){
    tempArr.clear()
    for(j in 0 until arr1.size) {
        if (arr1[i]== arr1[j]&&arr1[i]!= result[0])tempArr.add(arr1[i])
    }
    if(result2.isEmpty())result2 = tempArr.clone() as ArrayList<Int>
    else if (result2.size < tempArr.size) result2 = tempArr.clone() as ArrayList<Int>
}
result2.forEach { v->result.add(v) }
if(enemyIsPlayer) println("Result: $result, tempArr: $tempArr, tArr1 : $arr1")
result.forEach { if(!only2numresult.contains(it)) only2numresult.add(it) }
return only2numresult
}
/*
private fun mergeAndRemoveWillLOSE(): ArrayList<Int> {
val arr1:ArrayList<Int>
var tarr1 = ArrayList<Int>()
var result = ArrayList<Int>()
val tempArr  = ArrayList<Int>()
val tempUnchecked = ArrayList<Int>()


tarr1 = ArrayWillLOSE.clone() as ArrayList<Int>
ArrayWillLOSErelPlayer.forEach{v-> tarr1.add(v)}

arr1 = tarr1.clone() as ArrayList<Int>
tarr1.forEach{v-> if(!uncheckedList.contains(v))arr1.remove(v)}
uncheckedList.forEach{v-> if(!arr1.contains(v))tempUnchecked.add(v)}

println("tempuncheck $tempUnchecked")

if(tempUnchecked.isNotEmpty()){

    result = tempUnchecked
}else{
    for(i in 0 until arr1.size){
        tempArr.clear()
        for(j in 0 until arr1.size) {
            if (arr1[i]== arr1[j])tempArr.add(arr1[i])
        }
        if(result.isEmpty())result = tempArr.clone() as ArrayList<Int>
        else if (result.size > tempArr.size) result = tempArr.clone() as ArrayList<Int>
    }
}
println("result $result")
return result
}

*/


private fun finalMoveDecider(tMoveArr: ArrayList<Int>): Int {
val tempList = mutableListOf<Int>()
for (e in 0..(tMoveArr.size - 1)) {
    if (uncheckedList.contains(tMoveArr[e])) {
        tempList.add(tMoveArr[e])
    }
}

val m:Int

if(tempList.isNotEmpty() ) {
    m = tempList[(Math.random()* tempList.size).toInt()]
}
else  {
    //only as last instance if fail, so no crash:
    m = uncheckedList[(Math.random()* uncheckedList.size).toInt()]
}

//  println("$m bot move")
return m
}

private fun randomUncheckedListNumber(myArray: MutableList<Int>): Int {
return myArray.shuffled()[0]
}


private fun removeFromUncheckedList(remove: Int) {
uncheckedList.remove(remove)

}


private fun checkPlayerWin():Boolean {
//if player wins, addToBotLose, pass  tempBot/playerMoveArray, afterwards .clear(), this if needs to be first
if(tPlayerArray.size>2){
if ((tPlayerArray.containsAll(winList1)) ||
        (tPlayerArray.containsAll(winList2)) ||
        (tPlayerArray.containsAll(winList3)) ||
        (tPlayerArray.containsAll(winList4)) ||
        (tPlayerArray.containsAll(winList5)) ||
        (tPlayerArray.containsAll(winList6)) ||
        (tPlayerArray.containsAll(winList7)) ||
        (tPlayerArray.containsAll(winList8))) {

    addBotMapLOSE(whoStarted, parseTBotArr())     //also pass temp bot and player move strings
    addPlayerMapWIN(whoStarted, parseTPlayerArr())

    tBotArray.clear()
    tPlayerArray.clear()


    return true
}
}

return false


}

private fun checkBotWin():Boolean {

if(tBotArray.size>2){
if ((tBotArray.containsAll(winList1)) ||
        (tBotArray.containsAll(winList2)) ||
        (tBotArray.containsAll(winList3)) ||
        (tBotArray.containsAll(winList4)) ||
        (tBotArray.containsAll(winList5)) ||
        (tBotArray.containsAll(winList6)) ||
        (tBotArray.containsAll(winList7)) ||
        (tBotArray.containsAll(winList8))) {


    addBotMapWIN(whoStarted, parseTBotArr())//also pass temp bot and player move strings maybe
    addPlayerMapLOSE(whoStarted, parseTPlayerArr())

    tBotArray.clear()
    tPlayerArray.clear()

    return true
}
}


return false

}
private fun setDraw(){

addBotMapDRAW(whoStarted,parseTBotArr())
addplayerMapDRAW(whoStarted,parseTPlayerArr())

tBotArray.clear()
tPlayerArray.clear()
}

private fun addBotMapWIN(didStart: Int,tArr:ArrayList<Int>) {
//botWinMap(-1 to BotNotStart,1 to BotStart)
var countIndex= 0


if(didStart > 0){
    botMapWIN.forEach { t, _ ->
        if(t> 0){countIndex++}

    }

}else{
    botMapWIN.forEach { t, _ ->
        if(t< 0){countIndex++}

    }

}

botMapWIN[(countIndex+1)*(didStart)] = tArr

// negative numbers in map is bot didn't start, positive is bot did start
}
private fun addBotMapLOSE(didStart: Int,tArr:ArrayList<Int>) {
// negative numbers in map is bot didn't start, positive is bot did start
//botWinMap(-1 to BotNotStart,1 to BotStart)
var countIndex = 0
if(didStart > 0){
    botMapLOSE.forEach { t, _ ->
        if(t> 0){countIndex++}
    }

}else{
    botMapLOSE.forEach { t, _ ->
        if(t< 0){countIndex++}

    }

}

botMapLOSE[(countIndex + 1) * (didStart)] = tArr




//  println("bot map lose : $botMapLOSE")
}
private fun addBotMapDRAW(didStart: Int,tArr:ArrayList<Int>) {
// negative numbers in map is bot didn't start, positive is bot did start
//botWinMap(-1 to BotNotStart,1 to BotStart)
var countIndex = 0
if(didStart > 0){
    botMapDRAW.forEach { t, _ ->
        if(t> 0){countIndex++}
    }
}else{
    botMapDRAW.forEach { t, _ ->
        if(t< 0){countIndex++}
    }
}
botMapDRAW[(countIndex + 1) * (didStart)] = tArr
//    println("bot map draw : $botMapDRAW")
}


private fun addPlayerMapLOSE(didStart: Int,tArr:ArrayList<Int> /*actually botDidStart*/) {
//botWinPlayerMap(-1 to BotNotStart,1 to BotStart)
//So positive is actually when player loses and didn't start
var countIndex = 0
if(didStart > 0){
    playerMapLOSE.forEach { t, _ ->
        if(t> 0){countIndex++}
    }
}else{
    playerMapLOSE.forEach { t, _ ->
        if(t< 0){countIndex++}
    }
}
playerMapLOSE[(countIndex+1)*(didStart)] = tArr
//   println("player map lose : $playerMapLOSE")
}
private fun addPlayerMapWIN(didStart: Int,PArray:ArrayList<Int>) {
var countIndex = 0
if(didStart > 0){
    playerMapWIN.forEach { t, _ ->
        if(t> 0){countIndex++}
    }
}else{
    playerMapWIN.forEach { t, _ ->
        if(t< 0){countIndex++}
    }
}
//   println("Player map win before : $playerMapWIN")
playerMapWIN[(countIndex+1)*(didStart)]=PArray
//  println("Player map win after : $playerMapWIN")
}
private fun addplayerMapDRAW(didStart: Int,PArray:ArrayList<Int>) {
var countIndex = 0
if(didStart > 0){
    playerMapDRAW.forEach { t, _ ->
        if(t> 0){countIndex++}
    }
}else{
    playerMapDRAW.forEach { t, _ ->
        if(t< 0){countIndex++}
    }
}
playerMapDRAW[(countIndex+1)*(didStart)]=PArray
}


fun setStarted(who:Int){
whoStarted = who
}

fun addToTPlayerArray(tLastPlayerMove:Int)/*:Returnbot*/{
if(tLastPlayerMove !== 0) {
    tPlayerArray.add(tLastPlayerMove)
    removeFromUncheckedList(tLastPlayerMove)
}
}

fun addToTBotArray():Int{
gameBrain()

val tempList = mutableListOf<Int>()
for (e in 0..(BotMovesWIN.size - 1)) {
    if (uncheckedList.contains(BotMovesWIN[e])) {
        tempList.add(BotMovesWIN[e])
    }
}
val m:Int
when{
    tempList.isNotEmpty() -> {

        val random = Random()
        val check =random.nextInt(tempList.size)
        m = tempList[check]
    }
    else -> {
        //only as last instance if fail, so no crash:
        val random = Random()
        val check =random.nextInt(uncheckedList.size)
        m = uncheckedList[check]
    }
}

finalBotMove = m
tBotArray.add(finalBotMove)
removeFromUncheckedList(finalBotMove)

return finalBotMove
}

fun checkForWin():Returnbot{
var botMove = 0
var won = 0

when(true){
    checkPlayerWin()->{
        botMove = 0
        won = 555
        showTheWIN.add(2)
        resetBP()
    }
    checkBotWin()->{
        botMove = finalBotMove
        won = 666
        showTheWIN.add(1)
        resetBP()
    }
    uncheckedList.isEmpty()->{
        var countIndex = 0
        if(whoStarted > 0){botMapDRAW.forEach { t, _ -> if(t> 0)countIndex++}}
        else{botMapDRAW.forEach { t, _ -> if(t< 0)countIndex++}}

        botMapDRAW[(countIndex + 1) * (whoStarted)] = parseTBotArr()
        countIndex = 0
        if(whoStarted > 0){
            playerMapDRAW.forEach { t, _ ->
                if(t> 0){countIndex++}
            }
        }else{
            playerMapDRAW.forEach { t, _ ->
                if(t< 0){countIndex++}
            }
        }
        playerMapDRAW[(countIndex+1)*(whoStarted)]=parseTPlayerArr()
        botMove = 0
        won = 111
        showTheWIN.add(0)
        resetBP()

    }
}
return Returnbot(botMove,won)
}
fun getBotMove():Int{
return  finalBotMove
}

fun returnTheMaps(){
/* println("botMapWIN $botMapWIN")
println("playerMapLOSE $playerMapLOSE")
println("botMapDRAW $botMapDRAW")
println("playerMapDRAW $playerMapDRAW")
println("botMapLOSE $botMapLOSE")
println("playerMapWIN $playerMapWIN")
*/
}
fun printPlayerMapWIN(){
//  println("Player map win : $playerMapWIN")

}

private fun parseTPlayerArr():ArrayList<Int>{

val tArr  = arrayListOf<Int>()
for(i in 0 until tPlayerArray.size){
    tArr.add(tPlayerArray[i])
}
return tArr
}
private fun parseTBotArr():ArrayList<Int>{

val tArr = arrayListOf<Int>()
for(i in 0 until tBotArray.size){
    tArr.add(tBotArray[i])
}
return tArr
}


private fun resetBP(){
tBotArray.clear()
tPlayerArray.clear()
tempTurnCount = 1
whoStarted *= -1
uncheckedList = mutableListOf<Int>(1, 2, 3, 4, 5, 6, 7, 8, 9)

}

fun findError(){
// println("$tPlayerArray ->player, $tBotArray -> bot")

}
fun showWin(){
println("the win string : $showTheWIN")

}

/* fun printall(){
println("Botmaplose $botMapLOSE")
println("Botmapwin $botMapWIN")
println("Botmapdraw $botMapDRAW")

}
*/
}
/*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
                   |Algorithms|
* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
* =====================================================================================================================
* #1
*   botMapLOSE.isNotEmpty()->{
    //    println(" botMapLOSE.isNotEmpty()")
      //  println("botmaplose: $botMapLOSE,who started: $whoStarted, botarr $tBotArray")
        botMapLOSE.forEach { k, v ->
            if (k * whoStarted > 0 && v.size > tBotArray.size) {
                var doBreak = false
                for (j in 0..tBotArray.size - 2) {
                    if (doBreak)break
                    for (i in j until tBotArray.size) {
                        if (v[i] == tBotArray[i]) {

                            if(i == tBotArray.size-1) {
                                ArrayWillLOSE.add(v[i+1])
                                doBreak = true
                                break
                            }
                        }
                    }
                }
            }
        }

        var countIndex = 0
        if(whoStarted > 0){
            playerMapWIN.forEach { t, _ ->
                if(t> 0){countIndex++}

            }
        }else{
            playerMapWIN.forEach { t, _ ->
                if(t< 0){countIndex++}

            }
        }
        var doBreak = false
        for(j in 0..(tPlayerArray.size-2)){
         //   println("$j j")
            if(doBreak)break
            playerMapWIN.forEach{k,v-> if(k* whoStarted>0&& botMapLOSE[k]!!.size> tPlayerArray.size){
                for (i in j until tPlayerArray.size ){

                    if (v[i]== tPlayerArray[i] ) {
                        if (i == tPlayerArray.size - 1) {
                            if (1 == 1) {
                                ArrayWillLOSErelPlayer.add(botMapLOSE[k]!![i + 1])

                                //#25 println("$countIndex countindex, $j j, $i i , $k k")
                            } else {
                                ArrayWillLOSErelPlayer.add(botMapLOSE[k]!![i])
                            }
                            if ((countIndex * 1 == k) && ArrayWillLOSErelPlayer.isNotEmpty()) {
                              //  println(" doBreak = true")
                                doBreak = true
                                break
                            }
                        }else continue

                    }else break

                }
            }
            }

        }

       // println(" ArrayWillLOSE: $ArrayWillLOSE ,relplayer: $ArrayWillLOSErelPlayer  okokokokokokokokokokokokokok")
        //

        BotMovesWIN = mergeAndRemoveWillLOSE().clone() as ArrayList<Int>
        }
___________________________________________________________________________________________________________________________
private fun mergeAndRemoveWillLOSE(): ArrayList<Int> {
var arr1:ArrayList<Int>
var tarr1 = ArrayList<Int>()
var result = ArrayList<Int>()
var tempArr  = ArrayList<Int>()
var tempUnchecked = ArrayList<Int>()

ArrayWillLOSE.forEach{v->tarr1.add(v)}
ArrayWillLOSErelPlayer.forEach{v-> tarr1.add(v)}

arr1 = tarr1.clone() as ArrayList<Int>
tarr1.forEach{v-> if(!uncheckedList.contains(v))arr1.remove(v)}
uncheckedList.forEach{v-> if(!arr1.contains(v))tempUnchecked.add(v)}
//  println("tempuncheck $tempUnchecked")
if(tempUnchecked.isNotEmpty()){

    result = tempUnchecked
}else{
    for(i in 0 until arr1.size){
        tempArr.clear()
        for(j in 0 until arr1.size) {
            if (arr1[i]== arr1[j])tempArr.add(arr1[i])
        }
        if(result.isEmpty())result = tempArr.clone() as ArrayList<Int>
        else if (result.size > tempArr.size) result = tempArr.clone() as ArrayList<Int>
    }
}
return result
}


===========================================================================================================================
*#2
*
*  if(botMapLOSE.isNotEmpty()){
    playerMapWIN.forEach { k, v -> if(k* whoStarted>0&& botMapLOSE[k]!!.size> tPlayerArray.size) {

        for (i in 0 until tPlayerArray.size){
            if(v[i]== tPlayerArray[i]){
                if(i == tPlayerArray.size-1) ArrayCouldWINrelPlayer.add(v[i+1])
            }else break
        }
    }

    }

}


var tmpP = doPlayersMove()
if(tmpP.isNotEmpty()) BotMovesWIN.add(tmpP[0])
____________________________________________________________________________________________________________________________
private fun doPlayersMove():ArrayList<Int>{
var arr1 = ArrayList<Int>()
var tarr1 = ArrayList<Int>()
var tempArr  = ArrayList<Int>()
var result = ArrayList<Int>()
tarr1 = ArrayCouldWINrelPlayer.clone() as ArrayList<Int>

arr1 = tarr1.clone() as ArrayList<Int>
tarr1.forEach {v-> if(!uncheckedList.contains(v))arr1.remove(v)}
for(i in 0 until arr1.size){
    tempArr.clear()
    for(j in 0 until arr1.size) {
        if (arr1[i]== arr1[j])tempArr.add(arr1[i])
    }
    if(result.isEmpty())result = tempArr.clone() as ArrayList<Int>
    else if (result.size < tempArr.size) result = tempArr.clone() as ArrayList<Int>
}
return result
}
===========================================================================================================================
#3
   if(tPlayerArray.contains(2)&& tPlayerArray.contains(3)||
            tPlayerArray.contains(5)&& tPlayerArray.contains(9)||
            tPlayerArray.contains(4)&& tPlayerArray.contains(7)) BotMovesWIN.add(1)
    if(tPlayerArray.contains(1)&& tPlayerArray.contains(2)||
            tPlayerArray.contains(7)&& tPlayerArray.contains(5)||
            tPlayerArray.contains(9)&& tPlayerArray.contains(6)) BotMovesWIN.add(3)
    if(tPlayerArray.contains(1)&& tPlayerArray.contains(4)||
            tPlayerArray.contains(8)&& tPlayerArray.contains(9)||
            tPlayerArray.contains(5)&& tPlayerArray.contains(3)) BotMovesWIN.add(7)
    if(tPlayerArray.contains(1)&& tPlayerArray.contains(5)||
            tPlayerArray.contains(7)&& tPlayerArray.contains(8)||
            tPlayerArray.contains(3)&& tPlayerArray.contains(6)) BotMovesWIN.add(9)
    if(tPlayerArray.contains(1)&& tPlayerArray.contains(3)||
            tPlayerArray.contains(5)&& tPlayerArray.contains(8)) BotMovesWIN.add(2)
    if(tPlayerArray.contains(1)&& tPlayerArray.contains(7)||
            tPlayerArray.contains(5)&& tPlayerArray.contains(6)) BotMovesWIN.add(4)
    if(tPlayerArray.contains(4)&& tPlayerArray.contains(5)||
            tPlayerArray.contains(3)&& tPlayerArray.contains(9)) BotMovesWIN.add(6)
    if(tPlayerArray.contains(7)&& tPlayerArray.contains(9)||
            tPlayerArray.contains(2)&& tPlayerArray.contains(5)) BotMovesWIN.add(8)
    if(tPlayerArray.contains(1)&& tPlayerArray.contains(9)||
            tPlayerArray.contains(4)&& tPlayerArray.contains(6)||
            tPlayerArray.contains(2)&& tPlayerArray.contains(8)) BotMovesWIN.add(5)
========================================================================================================================
* */

