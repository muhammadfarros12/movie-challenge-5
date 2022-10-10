package com.farroos.movie.ui.home


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.farroos.movie.data.remote.home.Movie
import com.farroos.movie.data.remote.home.MovieResponse
import com.farroos.movie.data.service.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel : ViewModel() {

    /*  private val apiService: ApiService by lazy {
          ApiService.invoke()
      }*/

/*     val result = MutableLiveData<MovieResponse>()
     val loadingState = MutableLiveData<Boolean>()
     val errorState = MutableLiveData<Pair<Boolean, Exception?>>()*/

    private var _nowPlaying = MutableLiveData<List<Movie>>()
    val nowPlaying: LiveData<List<Movie>> get() = _nowPlaying

    private var _errorStatus = MutableLiveData<Boolean>()
    val errorStatus: LiveData<Boolean> get() = _errorStatus

    private var _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    init {
        getPlayingNowMovie()
    }

    fun getPlayingNowMovie() {
        ApiClient.instance.getMovie().enqueue(object : Callback<MovieResponse> {
            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                _loading.value = true
                when {
                    response.isSuccessful -> {
                        response.body()?.results?.let {
                            _loading.value = false
                            _nowPlaying.value = it
                            _errorStatus.value = false
                        }
                    }
                    response.code() == 401 -> {
                        _loading.value = false
                        _errorStatus.value = true
                    }
                    response.code() == 404 -> {
                        _loading.value = false
                        _errorStatus.value = true
                    }
                }
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                _errorStatus.value = true
                _loading.value = false
            }

        })
    }
/*

    fun getPlayingNow(){
        loadingState.postValue(true)
        errorState.postValue(Pair(false, null))
        viewModelScope.launch(Dispatchers.IO) {
            try {
                viewModelScope.launch(Dispatchers.Main) {
                    result.postValue(apiService.getMovie())
                    loadingState.postValue(false)
                    errorState.postValue(Pair(false, null))
                }
            } catch (e: Exception){
                viewModelScope.launch(Dispatchers.Main) {
                    loadingState.postValue(false)
                    errorState.postValue(Pair(true, e))
                }
            }
        }
    }
*/


}