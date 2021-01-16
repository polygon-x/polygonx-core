package io.polygonx.core.model;

import java.time.LocalDateTime;
import java.util.Objects;

public class User {
  private Integer id;
  private String username;
  private String email;
  private String password;
  private Boolean enabled;
  private String lastLoginIP;
  private LocalDateTime lastLoginTime;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;

  public User() {
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public Boolean getEnabled() {
    return enabled;
  }

  public void setEnabled(Boolean enabled) {
    this.enabled = enabled;
  }

  public String getLastLoginIP() {
    return lastLoginIP;
  }

  public void setLastLoginIP(String lastLoginIP) {
    this.lastLoginIP = lastLoginIP;
  }

  public LocalDateTime getLastLoginTime() {
    return lastLoginTime;
  }

  public void setLastLoginTime(LocalDateTime lastLoginTime) {
    this.lastLoginTime = lastLoginTime;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }

  public LocalDateTime getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(LocalDateTime updatedAt) {
    this.updatedAt = updatedAt;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    User user = (User) o;
    return Objects.equals(id, user.id) && Objects.equals(username, user.username) && Objects.equals(email, user.email) && Objects.equals(password, user.password) && Objects.equals(enabled, user.enabled) && Objects.equals(lastLoginIP, user.lastLoginIP) && Objects.equals(lastLoginTime, user.lastLoginTime) && Objects.equals(createdAt, user.createdAt) && Objects.equals(updatedAt, user.updatedAt);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, username, email, password, enabled, lastLoginIP, lastLoginTime, createdAt, updatedAt);
  }

  @Override
  public String toString() {
    return "User{" +
      "id=" + id +
      ", username='" + username + '\'' +
      ", email='" + email + '\'' +
      ", password='" + password + '\'' +
      ", enabled=" + enabled +
      ", lastLoginIP='" + lastLoginIP + '\'' +
      ", lastLoginTime=" + lastLoginTime +
      ", createdAt=" + createdAt +
      ", updatedAt=" + updatedAt +
      '}';
  }
}
