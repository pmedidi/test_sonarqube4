public class SpecialTeamMember implements TeamMember, SpecialTeamInterface {
    private String name;
    private String email;
    private String role;
    private int relevance;

    public SpecialTeamMember(String name, String email, String role, int relevance) {
        this.name = name;
        this.email = email;
        this.role = role;
        this.relevance = relevance;
    }

    public String getName() {
        return this.name;
    }

    public String getEmail() {
        return this.email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public int getRelevance() {
        return relevance;
    }

    public void setRelevance(int relevance) {
        this.relevance = relevance;
    }
}
