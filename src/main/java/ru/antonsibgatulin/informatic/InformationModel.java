package ru.antonsibgatulin.informatic;

import org.json.JSONObject;

import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table(name = "information_task",schema="public")
public class InformationModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id",nullable=false,insertable=true,updatable=true)
	public Integer id = null;
	public String texts = null;
	public String solution = null;
	public Integer type = null;

	public Long views = null;
	public Long likes = null;
	public Long dislike = null;
	public Long comments = null;
	public Long time_of_public = null;
	public String lang = null;
	public String input_data_json = null;
	public String output_data_json = null;
	public String json = null;
	public String author = null;

	public InformationModel() {

	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "type")
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	@Column(name = "texts")
	public String getTexts() {
		return texts;// .replaceAll("\n","<div class=\"new-wrap\"></div>");
	}

	public void setTexts(String text) {
		this.texts = text;
	}

	@Column(name = "solution")
	public String getSolution() {
		return solution;
	}

	public void setSolution(String solution) {
		this.solution = solution;
	}

	@Column(name = "views")
	public Long getViews() {
		return views;
	}

	public void setViews(Long views) {
		this.views = views;
	}

	@Column(name = "likes")
	public Long getLikes() {
		return likes;
	}

	public void setLikes(Long like) {
		this.likes = like;
	}

	@Column(name = "dislike")
	public Long getDislike() {
		return dislike;
	}

	public void setDislike(Long dislike) {
		this.dislike = dislike;
	}

	@Column(name = "comments")
	public Long getComments() {
		return comments;
	}

	public void setComments(Long comments) {
		this.comments = comments;
	}

	@Column(name = "time_of_public")
	public Long getTime_of_public() {
		return time_of_public;
	}

	public void setTime_of_public(Long time_of_public) {
		this.time_of_public = time_of_public;
	}

	@Column(name = "lang")
	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

	@Column(name = "input_data_json")
	public String getInput_data_json() {
		return input_data_json;
	}

	public void setInput_data_json(String input_data_json) {
		this.input_data_json = input_data_json;
	}

	@Column(name = "output_data_json")
	public String getOutput_data_json() {
		return output_data_json;
	}

	public void setOutput_data_json(String output_data_json) {
		this.output_data_json = output_data_json;
	}

	@Column(name = "author")
	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public JSONObject getJSON() {
		JSONObject jsonObject = new JSONObject();

		jsonObject.put("text", getTexts());
		jsonObject.put("id", getId());
		jsonObject.put("solution", getSolution());
		jsonObject.put("type", getType());
		jsonObject.put("answer",getOutput_data_json());

		return jsonObject;
	}


	public String getJSONString() {
		JSONObject jsonObject = new JSONObject();

		jsonObject.put("text", getTexts());
		jsonObject.put("id", getId());
		jsonObject.put("solution", getSolution());
		jsonObject.put("type", getType());
		jsonObject.put("answer",getOutput_data_json());

		return jsonObject.toString();
	}


	public String getTextSolution() {
		return solution.split("<code>")[0];
	}

	public String getCodeSolution() {
		String replaceStart = solution.split("<code>")[0];
		System.out.println(replaceStart);
		return solution.replace(replaceStart, "").replace("<code>", "").replace("</code>", "");
	}
	
	public String getDecidedSolution() {
		return getSolution().replaceAll("<code>", "<textarea id='code_d_" + getId() + "'>").replaceAll("</code>",
				"</textarea>");

	}
}
