// Import the functions you need from the SDKs you need
import { initializeApp } from "firebase/app";
import { getAnalytics } from "firebase/analytics";
import {getFirestore} from "firebase/firestore";
// TODO: Add SDKs for Firebase products that you want to use
// https://firebase.google.com/docs/web/setup#available-libraries

// Your web app's Firebase configuration
// For Firebase JS SDK v7.20.0 and later, measurementId is optional
const firebaseConfig = {
  apiKey: "AIzaSyDLb-upRukwfHuI8oKQFK2TJ0tq_csfAXI",
  authDomain: "englishcore-6cd88.firebaseapp.com",
  projectId: "englishcore-6cd88",
  storageBucket: "englishcore-6cd88.firebasestorage.app",
  messagingSenderId: "249441631183",
  appId: "1:249441631183:web:ebc3cf9decc56e8c4cb0ba",
  measurementId: "G-33NZM0HCPF"
};

// Initialize Firebase
const app = initializeApp(firebaseConfig);
const db = getFirestore(app);
const analytics = getAnalytics(app);
export default db;
