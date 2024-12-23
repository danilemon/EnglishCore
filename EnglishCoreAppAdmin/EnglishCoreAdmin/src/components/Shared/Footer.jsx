import React from 'react'
import '../../assets/styles/Footer.css';
import facebookIcon from '../../assets/icons/facebook_icon.png';

function Footer(){
    return (
        <div className='ftr-cont'>
            <div className='rights-div'>
            <p>2025®. All rights reserved</p>
            </div>

            <div className='author-eng-cor'>
            <a href="https://www.facebook.com/Escuela.Educacion.Ingles " target="_blank"  rel="noopener noreferrer">
            <img alt="Facebook Icon" className='ftr-icons' src={facebookIcon}/>
            </a>

            </div>

            <div className='priv-terms-div'> 
            <p>Terms & conditions</p>
            <p>Privacy policy</p>
            </div>
            
        </div>
      )
    }

export default Footer