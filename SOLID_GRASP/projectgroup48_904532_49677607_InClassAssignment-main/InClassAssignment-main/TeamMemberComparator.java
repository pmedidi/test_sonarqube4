import java.util.Comparator;

public class TeamMemberComparator implements Comparator<TeamMember> {
    public int compare(TeamMember t1, TeamMember t2) {
        if (t1 instanceof SpecialTeam) {
            SpecialTeam t1Comparable = ((SpecialTeam)t1);
            if (t2 instanceof SpecialTeam) {
                SpecialTeam t2Comparable = ((SpecialTeam) t2);
                return t2Comparable.getRelevance() - t1Comparable.getRelevance();
            } else {
                return t1Comparable.getRelevance();
            }
        }
        return (t2 instanceof SpecialTeam) ? ((SpecialTeam)t2).getRelevance() : 0;
    }
}
