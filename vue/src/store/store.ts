import { createStore, Store } from 'vuex'

interface RootState {
  openPopup: boolean
  searchClicked: boolean
}

const store = createStore<RootState>({
  state: {
    openPopup: false,
    searchClicked: false
  },
  mutations: {
    setOpenPopup(state, payload) {
      state.openPopup = payload;
    },
    setSearchClicked(state, payload) {
      state.searchClicked = payload
      console.log(state.searchClicked)
    }
  },
  actions: {
    updateOpenPopup({ commit }, payload) {
      commit('setOpenPopup', payload);
    },
    updateSearchClicked({ commit }, payload) {
      commit('setSearchClicked', payload)
    },
    
    
  }
})


export default store
