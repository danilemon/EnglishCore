import React from 'react'
import '../assets/styles/Home.css';
import Header from '../components/Shared/Header';
import Footer from '../components/Shared/Footer';
function Home(){
  return (
    <div className='home-page'>
      <header><Header/></header>
      <div className='greeting-div'>Buenas noches, Alex</div>
      <div className='home-cont'>

      </div>
      <footer><Footer/></footer>
    </div>
  )
}

export default Home;