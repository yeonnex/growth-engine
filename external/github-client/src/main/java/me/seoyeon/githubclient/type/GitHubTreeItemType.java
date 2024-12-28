package me.seoyeon.githubclient.type;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum GitHubTreeItemType {
  BLOB("blob"),
  TREE("tree");

  private final String type;

  GitHubTreeItemType(String type) {
    this.type = type;
  }

  /** JSON 데이터를 매핑할 때 호출되는 정적 팩토리 메서드 */
  @JsonCreator
  public static GitHubTreeItemType fromString(String type) {
    for (GitHubTreeItemType itemType : GitHubTreeItemType.values()) {
      if (itemType.type.equals(type)) return itemType;
    }
    throw new IllegalArgumentException("Unknown type: " + type);
  }
}
