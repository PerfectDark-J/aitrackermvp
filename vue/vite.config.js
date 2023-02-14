import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'



// https://vitejs.dev/config/
export default defineConfig({
  plugins: [vue()],
  build: {
    rollupOptions: {
      external: [
        'src/assets/Line-Graph.webp',
        '/dream-life-logo.png'
      ]
    }
  }
})

