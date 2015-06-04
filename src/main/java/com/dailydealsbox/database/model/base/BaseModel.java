/**
 * 
 */
package com.dailydealsbox.database.model.base;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author x_ye
 */
@MappedSuperclass
public abstract class BaseModel {

  @Id
  @Column(name = "id", insertable = false, updatable = false, unique = true, nullable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  /**
   * @return the id
   */
  public int getId() {
    return this.id;
  }

  /**
   * @param id
   *          the id to set
   */
  public void setId(int id) {
    this.id = id;
  }

  /*
   * (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    ObjectMapper mapper = new ObjectMapper();
    try {
      return mapper.writeValueAsString(this);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
      return "";
    }
  }

}
