package io.polygonx.core.model;

import java.time.LocalDateTime;
import java.util.Objects;

public class Contest {
  private Integer id;
  private Integer penalty; // minute as unit
  private Integer scoreboardFreezeDuration; // minute as unit
  private String name;
  private String shortName;
  private LocalDateTime startTime;
  private LocalDateTime endTime;
  private LocalDateTime createdTime;
  private LocalDateTime updatedTime;

  public Contest() {
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Integer getPenalty() {
    return penalty;
  }

  public void setPenalty(Integer penalty) {
    this.penalty = penalty;
  }

  public Integer getScoreboardFreezeDuration() {
    return scoreboardFreezeDuration;
  }

  public void setScoreboardFreezeDuration(Integer scoreboardFreezeDuration) {
    this.scoreboardFreezeDuration = scoreboardFreezeDuration;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getShortName() {
    return shortName;
  }

  public void setShortName(String shortName) {
    this.shortName = shortName;
  }

  public LocalDateTime getStartTime() {
    return startTime;
  }

  public void setStartTime(LocalDateTime startTime) {
    this.startTime = startTime;
  }

  public LocalDateTime getEndTime() {
    return endTime;
  }

  public void setEndTime(LocalDateTime endTime) {
    this.endTime = endTime;
  }

  public LocalDateTime getCreatedTime() {
    return createdTime;
  }

  public void setCreatedTime(LocalDateTime createdTime) {
    this.createdTime = createdTime;
  }

  public LocalDateTime getUpdatedTime() {
    return updatedTime;
  }

  public void setUpdatedTime(LocalDateTime updatedTime) {
    this.updatedTime = updatedTime;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Contest contest = (Contest) o;
    return Objects.equals(id, contest.id) && Objects.equals(penalty, contest.penalty) && Objects.equals(scoreboardFreezeDuration, contest.scoreboardFreezeDuration) && Objects.equals(name, contest.name) && Objects.equals(shortName, contest.shortName) && Objects.equals(startTime, contest.startTime) && Objects.equals(endTime, contest.endTime) && Objects.equals(createdTime, contest.createdTime) && Objects.equals(updatedTime, contest.updatedTime);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, penalty, scoreboardFreezeDuration, name, shortName, startTime, endTime, createdTime, updatedTime);
  }

  @Override
  public String toString() {
    return "Contest{" +
      "id=" + id +
      ", penalty=" + penalty +
      ", scoreboardFreezeDuration=" + scoreboardFreezeDuration +
      ", name='" + name + '\'' +
      ", shortName='" + shortName + '\'' +
      ", startTime=" + startTime +
      ", endTime=" + endTime +
      ", createdTime=" + createdTime +
      ", updatedTime=" + updatedTime +
      '}';
  }
}
