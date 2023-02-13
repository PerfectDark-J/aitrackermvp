import { createStore, Store } from 'vuex'

interface RootState {
  openPopup: boolean
}

const store = createStore<RootState>({
  state: {
    openPopup: false
  },
  mutations: {
    setOpenPopup(state, payload) {
      state.openPopup = payload;
    }
  },
  actions: {
    updateOpenPopup({ commit }, payload) {
      commit('setOpenPopup', payload);
    },
  }
})

export default store
