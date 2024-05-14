//package lms.be.function;
//
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.Random;
//
//public class GenTKB {
//    public List<MapTeacherSubjectTeam> mapTeacherSubjectTeams;
//    public Map<String, Map<String, Map<String, MapTeacherSubjectTeam>>> waiting;
//    private final int lessonPerDay = 5;
//    private final int endOfWeek = 7;
//    private Map<String, Map<String, Map<String, MapTeacherSubjectTeam>>> schedule;
//    private Map<String, Map<String, String>> teachersBusy;
//    private Map<Integer, Map<String, Map<String, String>>> labsBusy;
//    private List<Lab> labs;
//    private List<Team> teams;
//    private List<Teacher> teachers;
//    private List<Map<String, Object>> maps;
//
//    private int score = 10000;
//    // Các giảm điểm
//    private final int mSportLast = -1000;
//    private final int mSpacingLesson = -50;
//    private final int mManySubjectSameDay = -30;
//    private final int mHasChildrenInFirst = -100;
//    private final int mCoupleLessonSameDay = -1000;
//    private final int mNearDay = -30;
//    private final int mImportantInLast = -50;
//    private final int mTeacherBusy = -200;
//    private final int mTeacherWantSkip = -50;
//
//    public GenTKB(List<Teacher> teachers, List<Lab> labs, List<Team> teams, List<Map<String, Object>> maps) {
//        this.teachers = teachers;
//        this.labs = labs;
//        this.teams = teams;
//        this.maps = maps;
//
//        resetData();
//    }
//
//    public void resetData() {
//        mapTeacherSubjectTeams = new ArrayList<>();
//        waiting = new HashMap<>();
//        schedule = new HashMap<>();
//        teachersBusy = new HashMap<>();
//        labsBusy = new HashMap<>();
//
//        // Xuất các bản đồ
//        for (Map<String, Object> map : maps) {
//            MapTeacherSubjectTeam mapTeacherSubjectTeam = new MapTeacherSubjectTeam(map);
//            mapTeacherSubjectTeams.add(mapTeacherSubjectTeam);
//
//            // Thêm vào danh sách chờ
//            waiting.computeIfAbsent(mapTeacherSubjectTeam.code, k -> new HashMap<>()).put(mapTeacherSubjectTeam.code, mapTeacherSubjectTeam);
//        }
//
//        // Sắp xếp theo ưu tiên của môn học
//        Collections.sort(mapTeacherSubjectTeams);
//
//        // Thiết lập thời gian trống cho giáo viên và phòng lab
//        for (Teacher teacher : teachers) {
//            Map<String, String> busyMap = new HashMap<>();
//            for (int i = 2; i <= endOfWeek; i++) {
//                Map<String, String> dayMap = new HashMap<>();
//                for (int j = 1; j <= lessonPerDay; j++) {
//                    dayMap.put("T" + j, "");
//                }
//                busyMap.put("TH" + i, dayMap);
//            }
//            teachersBusy.put(teacher.name, busyMap);
//        }
//
//        for (Lab lab : labs) {
//            Map<String, Map<String, String>> labMap = new HashMap<>();
//            for (int i = 2; i <= endOfWeek; i++) {
//                Map<String, String> dayMap = new HashMap<>();
//                for (int j = 1; j <= lessonPerDay; j++) {
//                    dayMap.put("T" + j, "");
//                }
//                labMap.put("TH" + i, dayMap);
//            }
//            labsBusy.put(lab.id, labMap);
//        }
//    }
//
//    public void generateBase() {
//        for (Team team : teams) {
//            // Thiết lập các ngày nghỉ
//            setDayOff(team);
//
//            // Xếp lịch học
//            for (int th = 2; th <= endOfWeek; th++) {
//                for (int t = 1; t <= lessonPerDay; t++) {
//                    // Nếu đã có môn học được xếp vào thời gian này thì bỏ qua
//                    if (!schedule.get(team.name).get("TH" + th).get("T" + t).subjectName.equals("")) {
//                        continue;
//                    }
//
//                    // Tìm một môn học phù hợp
//                    MapTeacherSubjectTeam option = getReadyOption(th, t, team, false);
//                    if (option == null) {
//                        return;
//                    } else if (option.code.equals("DONE")) {
//                        break;
//                    } else {
//                        setSchedule(th, t, option);
//                    }
//                }
//            }
//        }
//    }
//
//    // Lấy một môn học phù hợp cho lớp, không quan tâm ràng buộc
//    public MapTeacherSubjectTeam getReadyOption(int th, int t, Team team, boolean strict) {
//        List<MapTeacherSubjectTeam> options = findWaitingLessonsOfTeam(team);
//        if (options.size() == 0) {
//            return null;
//        }
//
//        Collections.shuffle(options);
//
//        for (MapTeacherSubjectTeam item : options) {
//            // Kiểm tra ràng buộc của môn học
//            // Các điều kiện kiểm tra ràng buộc ở đây...
//
//            return item;
//        }
//
//        return null;
//    }
//
//    public void setSchedule(int th, int t, MapTeacherSubjectTeam map) {
//        // Ghi TKB
//        for (int i = 0; i < map.subjectBlock; i++) {
//            schedule.get(map.teamName).get("TH" + th).put("T" + (t + i), map);
//
//            // Ghi phòng lab bận
//            if (map.subjectLabId != null) {
//                labsBusy.get(map.subjectLabId).get("TH" + th).put("T" + (t + i), map.teamName + " - " + map.subjectName);
//            }
//
//            // Ghi giáo viên bận
//            teachersBusy.get(map.teacherName).get("TH" + th).put("T" + (t + i), map.teamName + " - " + map.subjectName);
//        }
//
//        if (waiting.containsKey(map.code)) {
//            Map<String, Map<String, Map<String, MapTeacherSubjectTeam>>> teamMap = waiting.get(map.code);
//            Map<String, Map<String, MapTeacherSubjectTeam>> thMap = teamMap.get(map.code);
//            Map<String, MapTeacherSubjectTeam> tMap = thMap.get(map.code);
//            tMap.get(map.code).numberOfLesson -= map.subjectBlock;
//            if (tMap.get(map.code).numberOfLesson == 0) {
//                tMap.remove(map.code);
//                if (thMap.get(map.code).isEmpty()) {
//                    thMap.remove(map.code);
//                    if (teamMap.get(map.code).isEmpty()) {
//                        teamMap.remove(map.code);
//                    }
//                }
//            }
//        }
//    }
//
//    public void setDayOff(Team team) {
//        // Thiết lập giờ chào cờ
//        MapTeacherSubjectTeam off = new MapTeacherSubjectTeam();
//        off.subjectBlock = 1;
//        off.subjectName = "-";
//        off.teamName = team.name;
//        setSchedule(2, 1, off);
//
//        // Thiết lập giờ sinh hoạt
//        Map<String, Object> map = findOption(Collections.singletonMap("teacher_team_id", team.id));
//        if (map != null) {
//            MapTeacherSubjectTeam shMap = findOption(Collections.singletonMap("subjectName", "SH " + team.name.charAt(0)));
//            if (shMap.teacherName.equals("Tân")) {
//                setSchedule(6, 5, shMap);
//            } else {
//                setSchedule(7, 5, shMap);
//            }
//        }
//
//        // Thiết lập ngày có 4 tiết
//        int t = lessonPerDay;
//        char group = team.name.charAt(0);
//        List<Integer> ths = new ArrayList<>();
//
//        if (group == '6') {
//            Collections.addAll(ths, 3, 5, 6);
//        }
//        if (group == '7') {
//            Collections.addAll(ths, 3, 6);
//        }
//        if (group == '9') {
//            ths.add(5);
//        }
//        for (int th : ths) {
//            MapTeacherSubjectTeam offDay = new MapTeacherSubjectTeam();
//            offDay.subjectBlock = 1;
//            offDay.subjectName = "-";
//            offDay.teamName = team.name;
//            setSchedule(th, t, offDay);
//        }
//    }
//
//    public List<MapTeacherSubjectTeam> findWaitingLessonsOfTeam(Team team) {
//        List<MapTeacherSubjectTeam> options = new ArrayList<>();
//        for (MapTeacherSubjectTeam item : mapTeacherSubjectTeams) {
//            if (item.teamId == team.id) {
//                options.add(item);
//            }
//        }
//        return options;
//    }
//
//    public MapTeacherSubjectTeam findOption(Map<String, Object> conditional) {
//        for (MapTeacherSubjectTeam item : mapTeacherSubjectTeams) {
//            boolean found = true;
//            for (Map.Entry<String, Object> entry : conditional.entrySet()) {
//                String key = entry.getKey();
//                Object value = entry.getValue();
//                if (!value.equals(item.getField(key))) {
//                    found = false;
//                    break;
//                }
//            }
//            if (found) {
//                return item;
//            }
//        }
//        return null;
//    }
//}
//
