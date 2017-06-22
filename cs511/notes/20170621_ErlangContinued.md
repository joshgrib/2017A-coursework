# 20170621

## Message Passing

### Interaction Models
Previously we had shared memory, semaphores and monitors.

BUT monitors have a problem -
* High centralized
* Not good for distributed applications

Message passing to the rescue!

### Message Passing
* A **channel** is a medium that connects a sending a receiving process
* A **message** is information exchanged between processes via a channel
