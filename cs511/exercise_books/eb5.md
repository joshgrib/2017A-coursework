## Problem 2
```java
Semaphore mutexM = new Semaphore(1);
Semaphore mutexW = new Semaphore(1);
Semaphore restroom = new Semaphore(4);//4 restrooms
thread M{
    mutexM.acquire();
    //first man? take the restroom from the women
    if(++m==1) mutexW.acquire();
    mutexM.release();

    restroom.acquire();
    //use restroom
    restroom.release();

    mutexM.acquire();
    //last man? release the bathroom to the women
    if(--m==0) mutexW.release();
    mutexM.release();

}
thread W{
    mutexW.acquire();
    //first women? take the restroom from the men
    if(++m==1) mutexM.acquire();
    mutexW.release();

    restroom.acquire();
    //use restroom
    restroom.release();

    mutexW.acquire();
    //last woman? release the bathroom to the men
    if(--m==0) mutexM.release();
    mutexW.release();
}
```

## Problem 3
```java
int N = 50;
Semaphore[] canBoard = {0, 0};
Semaphore canExit = new Semaphore(0);
Semaphore passengerBoarded = new Semaphore(0);
Semaphore passengerExited = new Semaphore(0);

thread Ferry{
    int coast = 0;
    while(true){
        repeat(N) canBoard[coast].release();
        repeat(N) passengerBoarded.acquire();
        coast = coast+1%2;
        repeat(N) canExit.release();
        repeat(N) passengerExited.acquire();
    }
}
thread Passenger(coast){
    canBoard[coast].acquire();
    passengerBoarded.release();
    canExit.acquire();
    passengerExited.release();
}
```

## Problem 6
