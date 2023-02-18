<template>
  <div class="fireworks-container">
    <div class="firework"></div>
    <div class="firework"></div>
    <div class="firework"></div>
    <div class="firework"></div>
    <div class="firework"></div>
  </div>
</template>
<script>
import anime from 'animejs'
export default {
name: 'FireworksAnimation',
data () {
return {
animeInstance: null
}
},
mounted () {
this.animeInstance = anime({
targets: '.firework',
translateY: [-500, 0],
translateX: (el, i) => {
return anime.random(-500, 500)
},
opacity: [0, 1],
easing: 'easeOutSine',
duration: 800,
delay: (el, i) => i * 400,
complete: () => {
this.animeInstance = anime({
targets: '.firework',
translateY: (el) => {
return el.getBoundingClientRect().top + window.scrollY + 200
},
translateX: (el, i) => {
return anime.random(-500, 500)
},
easing: 'easeInQuad',
duration: 500,
delay: (el, i) => i * 100,
complete: () => {
this.animeInstance = anime({
targets: '.firework',
translateY: (el) => {
return el.getBoundingClientRect().top + window.scrollY + 500
},
translateX: (el, i) => {
return anime.random(-500, 500)
},
opacity: [1, 0],
easing: 'easeInQuad',
duration: 500,
delay: (el, i) => i * 100
})
}
})
}
})
}
}
</script>

<style scoped>
.fireworks-container {
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100%;
  width: 100%;
}

.firework {
  position: absolute;
  top: 0;
  left: 0;
  width: 20px;
  height: 20px;
  border-radius: 50%;
  background-color: yellow;
}
</style>


