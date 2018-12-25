package testing


class test{
var botMapWIN = mutableMapOf<Int, ArrayList<Int>>(
        1 to arrayListOf(1,2,3,11),
        -2 to arrayListOf(1,2,31111,121111),
        3 to arrayListOf(1,5,1111,131111),
        4 to arrayListOf(1,5,3,14),
        5 to arrayListOf(1,2,3,15),
        6 to arrayListOf(3,2,3,16)
        )

    var playerMapLOSE = mutableMapOf<Int, ArrayList<Int>>(
            1 to arrayListOf(1,2,3,11),
            -2 to arrayListOf(4,4,4,12),
            3 to arrayListOf(4,4,4,13),
            4 to arrayListOf(1,5,3,14),
            5 to arrayListOf(1,2,3,15),
            6 to arrayListOf(3,2,3,16)
    )

var whoStarted = 1
var tBotArray =arrayListOf<Int>(1,2,3)
var tPlayerArray =arrayListOf<Int>(4,4,4)
var doBotWINMoveArr = arrayListOf<Int>()
var doBotWINMoveRelPlayer = arrayListOf<Int>()
fun brain(){


    botMapWIN.forEach { k, v -> if(k* whoStarted > 0 && v.size > tBotArray.size) {
        for (i in 0 until tBotArray.size) {
            if (v[i] == tBotArray[i]) {
                if (i == tBotArray.size - 1) doBotWINMoveArr.add(v[i+1])
                else continue
            }else break
        }
    }
    }
    println(doBotWINMoveArr)
    //do move based on botWin  OK

    playerMapLOSE.forEach { k, v -> if(k * whoStarted > 0){
        if(whoStarted>0 && botMapWIN[k]!!.size > tPlayerArray.size){
            //   if(enemyIsPlayer)println("WHostarted : $whoStarted is bigger zero and botmapwinKsize: ${botMapWIN[k]!!.size}")
            for(i in 0 until tPlayerArray.size){

                if(v[i]== tPlayerArray[i]){
                    //   if(enemyIsPlayer)println("if ${v[i]} == ${tPlayerArray[i]}")
                    if(i == tPlayerArray.size-1) {
                        //      if(enemyIsPlayer)println("if i == tPlayeraray.size-1 if $i == ${tPlayerArray.size-1}")
                        doBotWINMoveRelPlayer.add(botMapWIN[k]!![i+1])
                    }
                }else break
            }
        }else if(whoStarted<0 && botMapWIN[k]!!.size >= tPlayerArray.size){
            //     if(enemyIsPlayer)println("WHostarted : $whoStarted is smaller and botmapwinKsize: ${botMapWIN[k]!!.size}")
            for(i in 0 until tPlayerArray.size){

                if(v[i]== tPlayerArray[i]){
                    //   if(enemyIsPlayer) println("if ${v[i]} == ${tPlayerArray[i]}")
                    if(i == tPlayerArray.size-1){
                        // if(enemyIsPlayer)println("if i == tPlayeraray.size-1 if $i == ${tPlayerArray.size-1}")
                        doBotWINMoveRelPlayer.add(botMapWIN[k]!![i])
                    }
                }else break
            }
        }
    }

    }
    //do move based on botWINrelPLayer OK
    println(doBotWINMoveRelPlayer)


    var only2willLose = arrayListOf<Int>(5,1)
    var finalOnly2 = arrayListOf<Int>(1,1,1,2,2,2)

    if(only2willLose.size>1){
        finalOnly2.remove(only2willLose.first())
        finalOnly2.remove(only2willLose.last())
    }else if(only2willLose.isNotEmpty())finalOnly2.remove(only2willLose.first())
    println("finalOnly2 $finalOnly2")
}



}

fun main(args: Array<String>){
    test().brain()
}

/*
*  //do move based on botWin  OK
*
*
*
*
*
*
*
*
*
*
*
*
*
* */