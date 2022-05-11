package com.example.bakis;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import kotlin.jvm.Volatile;

@androidx.room.Database(entities = {Exercise.class, Course.class, CourseExerciseCrossRef.class, Statistic.class}, version = 2)
@TypeConverters(Converters.class)
public abstract class Database extends RoomDatabase {

    @Volatile
    private static Database instance;

    public abstract ExerciseDao exerciseDao();
    public abstract CourseDao courseDao();
    public abstract CourseExerciseCrossRefDao courseExerciseCrossRefDao();

    public static synchronized Database getInstance(Context context){
        if (instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    Database.class, "database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void>{
        private ExerciseDao exerciseDao;
        private CourseDao courseDao;
        private CourseExerciseCrossRefDao courseExerciseCrossRefDao;

        private PopulateDbAsyncTask(Database db){
            exerciseDao = db.exerciseDao();
            courseDao = db.courseDao();
            courseExerciseCrossRefDao = db.courseExerciseCrossRefDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            /*exerciseDao.insert(new Exercise("title 4", "desc 4", R.drawable.test2, null, false));
            exerciseDao.insert(new Exercise("title 3", "desc 3", R.drawable.test3, null, false));
            exerciseDao.insert(new Exercise("title 2", "desc 2", R.drawable.test2, null, false));
            exerciseDao.insert(new Exercise("title 1", "desc 1", R.drawable.test3, null, false));
            exerciseDao.insert(new Exercise("title 8", "desc 8", R.drawable.test2, null, false));
            exerciseDao.insert(new Exercise("title 7", "desc 7", R.drawable.test3, null, false));
            exerciseDao.insert(new Exercise("title 6", "desc 6", R.drawable.test2, null, false));
            exerciseDao.insert(new Exercise("title 5", "desc 5", R.drawable.test3, null, false));
            exerciseDao.insert(new Exercise("title 12", "desc 12", R.drawable.test2, null, false));
            exerciseDao.insert(new Exercise("title 11", "desc 11", R.drawable.test3, null, false));
            exerciseDao.insert(new Exercise("title 10", "desc 10", R.drawable.test2, null, false));
            exerciseDao.insert(new Exercise("title 9", "desc 9", R.drawable.test3, null, false));*/

            exerciseDao.insert(new Exercise("Bug",
                    "Start position: lie on your back, knees and hips bent 90 degrees (shinc parallel to the floor), arms straight up.\n\n" +
                    "Keeping the arms straight, lower the right hand behind the head and at the same time straighten and lower the left (opposite) leg on an exhale. " +
                            "Inhale and return to the start position. Alternate sides, completing 8-16 repetitions on each",
                    "Vabalas",
                    "Pradinė padėtis: gulėkite ant nugaros, keliai ir klubai sulenkti 90 laipsnių kampu (blauzdos lygiagrečiai grindims)" +
                            ", rankos tiesiai į viršų.\n\n Laikydami rankas tiesiai, nuleiskite dešinę ranką už galvos ir tuo pačiu ištieskite " +
                            "ir nuleiskite kairę (priešingą) koją ir iškvėpkite. Įkvėpkite ir grįžkite į pradinę padėtį. " +
                            "Pakeiskite puses, kiekvienoje atlikdami 8–16 pakartojimų", R.drawable.dead_bug, null, false));

            exerciseDao.insert(new Exercise("Heel slide", "Start position: lie on your back, legs straight, arms along the body.\n\n" +
                    "On an exhale, bending your knee, pull your right leg to yourself by sliding your heel along the floor toward your gluteal muscles. " +
                    "Straighten teh leg and return to its start position on the ingale. Repeat with the left leg. Complete 8 repetitions per side."
                    , "Kulno slidimas",
                    "Pradinė padėtis: gulėkite ant nugaros, kojos tiesios, rankos išilgai kūno.\n\nIškvėpdami, sulenkdami kelį," +
                            " patraukite dešinę koją prie savęs, traukdami kulną išilgai grindų link sėdmenų raumenų. " +
                            "Ištieskite koją ir grįžkite į pradinę padėtį ir įkvėpkite. Pakartokite su kaire koja. " +
                            "Atlikite 8 pakartojimus vienoje pusėje", R.drawable.heel_slide, null, false));

            exerciseDao.insert(new Exercise("Bug", "Start position: lie on your back, knees and hips bent 90 degrees (shinc parallel to the floor), arms straight up.\n\n" +
                    "Keeping the arms straight, lower the right hand behind the head and at the same time straighten and lower the left (opposite) leg on an exhale. Inhale and return to the start position. Alternate sides, completing 8-16 repetitions on each",
                    "Vabalas", "Pradinė padėtis: gulėkite ant nugaros, keliai ir klubai sulenkti 90 laipsnių kampu (blauzdos lygiagrečiai grindims), rankos tiesiai į viršų.\n\n Laikydami rankas tiesiai, nuleiskite dešinę ranką už galvos ir tuo pačiu ištieskite ir nuleiskite kairę (priešingą) koją ir iškvėpkite. Įkvėpkite ir grįžkite į pradinę padėtį. Pakeiskite puses, kiekvienoje atlikdami 8–16 pakartojimų", R.drawable.dead_bug, null, false));

            exerciseDao.insert(new Exercise("Heel slide", "Start position: lie on your back, legs straight, arms along the body.\n\nOn an exhale, bending your knee, pull your right leg to yourself by sliding your heel along the floor toward your gluteal muscles. Straighten teh leg and return to its start position on the ingale. Repeat with the left leg. Complete 8 repetitions per side."
                    , "Kulno slidimas", "Pradinė padėtis: gulėkite ant nugaros, kojos tiesios, rankos išilgai kūno.\n\nIškvėpdami, sulenkdami kelį, patraukite dešinę koją prie savęs, traukdami kulną išilgai grindų link sėdmenų raumenų. Ištieskite koją ir grįžkite į pradinę padėtį ir įkvėpkite. Pakartokite su kaire koja. Atlikite 8 pakartojimus vienoje pusėje", R.drawable.heel_slide, null, false));

            exerciseDao.insert(new Exercise("Bug", "Start position: lie on your back, knees and hips bent 90 degrees (shinc parallel to the floor), arms straight up.\n\n" +
                    "Keeping the arms straight, lower the right hand behind the head and at the same time straighten and lower the left (opposite) leg on an exhale. Inhale and return to the start position. Alternate sides, completing 8-16 repetitions on each",
                    "Vabalas", "Pradinė padėtis: gulėkite ant nugaros, keliai ir klubai sulenkti 90 laipsnių kampu (blauzdos lygiagrečiai grindims), rankos tiesiai į viršų.\n\n Laikydami rankas tiesiai, nuleiskite dešinę ranką už galvos ir tuo pačiu ištieskite ir nuleiskite kairę (priešingą) koją ir iškvėpkite. Įkvėpkite ir grįžkite į pradinę padėtį. Pakeiskite puses, kiekvienoje atlikdami 8–16 pakartojimų", R.drawable.dead_bug, null, false));

            exerciseDao.insert(new Exercise("Heel slide", "Start position: lie on your back, legs straight, arms along the body.\n\nOn an exhale, bending your knee, pull your right leg to yourself by sliding your heel along the floor toward your gluteal muscles. Straighten teh leg and return to its start position on the ingale. Repeat with the left leg. Complete 8 repetitions per side."
                    , "Kulno slidimas", "Pradinė padėtis: gulėkite ant nugaros, kojos tiesios, rankos išilgai kūno.\n\nIškvėpdami, sulenkdami kelį, patraukite dešinę koją prie savęs, traukdami kulną išilgai grindų link sėdmenų raumenų. Ištieskite koją ir grįžkite į pradinę padėtį ir įkvėpkite. Pakartokite su kaire koja. Atlikite 8 pakartojimus vienoje pusėje", R.drawable.heel_slide, null, false));



            /*courseDao.insertCourse(new Course("course1", false, true));
            courseDao.insertCourse(new Course("course2", false, false));
            courseDao.insertCourse(new Course("course3", false, false));
            courseDao.insertCourse(new Course("course4", false, true));*/

            courseDao.insertCourse(new Course("1Course name", "1Kurso pavadinimas", false, false));
            courseDao.insertCourse(new Course("2Course name", "2Kurso pavadinimas", false, true));
            courseDao.insertCourse(new Course("3Course name", "3Kurso pavadinimas", false, false));

            /*courseDao.insertCourse(new Course("Title4 in English", null, true, true));
            courseDao.insertCourse(new Course("Title5 in English", null, true, false));*/

            courseExerciseCrossRefDao.insertCourseExerciseCrossRef(new CourseExerciseCrossRef(1, 1, 20, 10));
            courseExerciseCrossRefDao.insertCourseExerciseCrossRef(new CourseExerciseCrossRef(1, 2, 15, 10));
            courseExerciseCrossRefDao.insertCourseExerciseCrossRef(new CourseExerciseCrossRef(1, 3, 10, 10));
            courseExerciseCrossRefDao.insertCourseExerciseCrossRef(new CourseExerciseCrossRef(1, 4, 5, 10));

            courseExerciseCrossRefDao.insertCourseExerciseCrossRef(new CourseExerciseCrossRef(2, 1, 10, 10));
            courseExerciseCrossRefDao.insertCourseExerciseCrossRef(new CourseExerciseCrossRef(2, 2, 5, 10));

            courseExerciseCrossRefDao.insertCourseExerciseCrossRef(new CourseExerciseCrossRef(3, 1, 5, 10));
            courseExerciseCrossRefDao.insertCourseExerciseCrossRef(new CourseExerciseCrossRef(3, 4, 10, 10));

            //LiveData<List<CourseWithExercises>> courseWithExercises = exerciseDao.getAllExercisesOfCourse(1);

            //courseWithExercises.getValue().get(1).

            /*courseWithExercises.

            System.out.println();*/

            return null;
        }
    }
}
