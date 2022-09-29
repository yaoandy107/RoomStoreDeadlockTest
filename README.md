# Room & Store4 Deadlock Test
A test sample for deadlock issue with Room and [Store4](https://github.com/MobileNativeFoundation/Store)

## Original problems
When calling `store.get()` in the `RoomDatabase.withTransaction {}` and the store trigger the writer to write data into the database, a deadlock will occur. Everything is fine if the writer does not trigger or calling the `store.get()` outside the `withTransaction {}`.

## To Reproduce
- clear the Room's table that the `Store` used
- call `store.get()` inside the `RoomDatabase.withTransaction {}`

## Reason
`withTransaction()` could only use the same coroutineScope that receive by the suspending block to perform blocking database operation. So this is the limitation of Room which let the usage of Store inside withTransaction() impossible
<img width="812" alt="Screen Shot 2022-09-27 at 9 52 25 PM" src="https://user-images.githubusercontent.com/20769836/192545374-3c30b1d5-47cd-4fab-ae2c-23d4077c7467.png">

## Related issue
https://github.com/MobileNativeFoundation/Store/issues/453
