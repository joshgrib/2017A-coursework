## Problem 4
```java
monitor ThreeeWaySequencer{
    int state = 0;
    condition firstPhase;
    condition secondPhase;
    condition thirdPhase;

    void first(){
        if(state != 0)
            firstPhase.wait();
        state = 1;
        secondPhase.signal();
    }
    void second(){
        if(state != 1)
            secondPhase.wait();
        state = 2;
        thirdPhase.signal();
    }
    void first(){
        if(state != 0)
            thirdPhase.wait();
        state = 0;
        firstPhase.signal();
    }
}
```

## Problem 5
```java
monitor Barrier{
    int N = 5;
    int count = 0;
    condition allHere;

    void wait(){
        count++;
        if(count<N)
            allHere.wait()
        allHere.signal();
        count = 0;
    }
}
```

## Problem 6
```java
monitor grid{
    int N = MAX_PRODUCERS;
    int producers = 0;
    int consumers = 0;
    condition isSurplus;
    condition producersNotFull;

    void startConsuming(){
        //no extra producers, we have to wait
        if((producers - consumers) < 1)
            isSurplus.wait()
        consumers++;
    }
    void stopConsuming(){
        consumers--;
        if((producers - consumers) > 1)
            isSurplus.signal()
    }
    void startProducing(){
        if(producers == N)
            producersNotFull.wait()
        producers++;
        if((producers - consumers) > 1)
            isSurplus.signal();
    }
    void stopProducing(){
        if((producers - consumers) < 1)
            isSurplus.wait()
            producers--;
        if((producers+1) == N)
            producersNotFull.signal()

    }
}
```
