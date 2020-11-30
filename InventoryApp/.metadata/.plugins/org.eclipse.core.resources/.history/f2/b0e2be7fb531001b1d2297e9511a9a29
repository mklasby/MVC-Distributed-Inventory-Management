package Model.TheatreModel;

import CommonMessage.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

public class BrowseMovie implements MessageConstants {
	private ArrayList<Movie> movieList;
	private boolean isRegUser;

	public Message BrowseMovie(ResultSet allMovies, boolean isRegUser) throws SQLException {
		setIsRegUser(isRegUser);
		do {
			Movie thisMovie = new Movie(allMovies);
			movieList.add(thisMovie);
		} while (allMovies.next());
		JSONArray movieData = new JSONArray();
		for (Movie movie : movieList) {
			movieData.put(movie.toString());
		}
		Message response = new Message(OK, movieData);
		return response;
	}

	public ArrayList<Movie> getMovieList() {
		return movieList;
	}

	public void setMovieList(ArrayList<Movie> movieList) {
		this.movieList = movieList;
	}

	public boolean isRegUser() {
		return isRegUser;
	}

	public void setIsRegUser(boolean isRegUser) {
		this.isRegUser = isRegUser;
	}

	private Movie parseMovieList(String movieName) { // How is the connection? we already have the array of movies
		for (Movie movieObj : movieList) { // Changed signature
			if (movieObj.getMovieName().equals(movieName))
				return movieObj;
		}
		return null;
	}

	public Movie selectMovie(String movieName) { // original signature selectMovie(ResultSet movie)
		Movie movie = parseMovieList(movieName);
		return movie;
	}

}
