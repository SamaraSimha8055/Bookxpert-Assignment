# Bookxpert-Assignment
An assignment test for Android Developer Role from Bookxpert Pvt Ltd.

Task includes below requirements :: 

1. User Authentication
-> Implement Google Sign-In using Firebase Authentication. 
-> Save user details in Room DB
2. Report (PDF Viewer) -> 
https://fssservices.bookxpert.co/GeneratedPDF/Companies/nadc/2024-
2025/BalanceSheet.pdf
-> Show above Pdf url in PDF Viewer or any third party library, can be used to 
display the PDF within the app.
3. Image Capture & Gallery Selection
-> Implement an option to capture an image using the camera.
 -> Allow users to pick an image from the gallery.
 -> Display the selected image in an ImageView.
4. Room DB Implementation
API Integration -> https://api.restful-api.dev/objects
-> Fetch the list of data from the provided API.
-> Store the details retrieved from the API into a Room DB.
-> Provide Update and Delete of Items for Stored Data
-> Error Handling
-> Validations
5. Push Notification
-> When Delete an Item (items getting from API) to Send real-time notifications using 
FCM.
-> Notification contains Deleted of the Items details
-> Allow users to Enable/Disable notifications via Shared Preferences/Preference 
DataStore.


Technical Requirements:
-> Use Kotlin
-> Modern UI/UX
-> Follow MVVM architecture.
-> Implement Retrofit for API calls.
-> App Logo
-> Handle Light and Dark Theme
-> Handle runtime permissions for camera and storage.
