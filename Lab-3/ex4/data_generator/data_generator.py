import random
import re
from calendar import monthrange
import json

def get_random_timestamp():
    date = "2020-12-" + str(random.randint(1,31)) + "T" + str(random.randint(10,23)) + ":" + str(random.randint(10,59)) + ":" + str(random.randint(10,59)) + "." + str(random.randint(100,999)) + "+0000"
    return date

fnames = []
lnames = []

num = 12
picked_names = []
emails = []
usernames = []
comments = []
movies = []
comments = []

with open("movies.txt") as m:
    for movie in m:
        # title, time, imdb, rotten tomatoes
        movies.append(movie.split("$%&"))

with open("comments.txt") as c:
    for cmt in c:
        comments.append(cmt)

with open("fnames.txt") as first:
    with open("lnames.txt") as last:
        for fname in first:
            fnames.append(fname)
        for lname in last:
            lnames.append(lname)  

for i in range(num):
    fname = random.choice(fnames)
    lname = random.choice(lnames)
    name = "{} {}".format(fname.strip(), lname.strip())
    while name in picked_names:
        fname = random.choice(fnames)
        lname = random.choice(lnames)
        name = "%s %s".format(fname.strip(), lname.strip())
    picked_names.append(name.replace("'", "\'"))

providers = ["@gmail.com", "@outlook.com", "@hotmail.com", "@sapo.pt", "@portugalmail.pt"]
for name in picked_names:
    emails.append(name.replace(" ", "_").lower() + str(random.randint(1950, 2020)) + random.choice(providers))
    usernames.append(name.replace(" ", "_").lower())

with open("inserts.txt", "w") as out:
    #line = "INSERT INTO user () VALUES (" + f"{}" + ");\n"

    for i in range(num):
        # users
        email = emails[i]
        name = picked_names[i]
        username = usernames[i]
        
        line = "INSERT INTO users (email, name, username) VALUES (" + f"\'{email}\', \'{name}\', \'{username}\'" + ");\n"
        out.write(line)

        # movies_by_rating
        title = movies[i][0]
        imdb = movies[i][2]
        time = movies[i][1]
        rt = movies[i][3]
        budget = movies[i][4]
        box_office = movies[i][5]
        directors = movies[i][6]
        producers = {i for i in movies[i][7].replace("\n","").replace("'","").strip('][').split(',')} if len(movies[i][7].split(",")) > 1 else {movies[i][7].replace("\n","")}
        producers = {i.replace(" ", "", 1) for i in producers} if len(producers) > 1 else producers
        while not producers:
            producers = {i for i in movies[i][7].replace("\n","").replace("'","").strip('][').split(',')} if len(movies[i][7].split(",")) > 1 else {movies[i][7].replace("\n","")}
            producers = {i.replace(" ", "", 1) for i in producers} if len(producers) > 1 else producers
        print(i)

        finances = {"Box Office": float(str(box_office).strip()), "Budget": float(budget)}
        line = "INSERT INTO movies_by_rating (id, title, imdb, rt, time, finances, directors, producers) VALUES (" + f"{i}, \'{title}\', {imdb}, {time}, {rt}, {finances}, {directors}, {producers}" + ");\n"
        out.write(line)

        # movie_reviews_by_timestamp
        content = comments[i]
        time = get_random_timestamp()
        line = "INSERT INTO movie_reviews_by_timestamp (id, content, time) VALUES (" + f"{i}, \'{content}\', \'{time}\'" + ");\n"
        out.write(line)

        # movies_seen_by_user
        username = usernames[i]
        lst = movies[random.randint(0, num):random.randint(0, num)] if not [] else movies[random.randint(0, num):random.randint(0, num)]
        seen = {movie[0] for movie in lst} if lst else {movie[0] for movie in movies[random.randint(0, num):random.randint(0, num)]}
        while not seen:
            seen = {movie[0] for movie in lst} if lst else {movie[0] for movie in movies[random.randint(0, num):random.randint(0, num)]}
        line = "INSERT INTO movies_seen_by_user (username, seen) VALUES (" + f"\'{username}\', {seen}" + ");\n"
        out.write(line)

        # user_reviews_by_movie
        username = usernames[i]
        content = comments[i]
        line = "INSERT INTO user_reviews_by_movie (vid_id, username, content) VALUES (" + f"{i}, \'{username}\', \'{content}\'" + ");\n"
        out.write(line)

        # reviews_by_movie
        title = movies[i][0]
        movie_id = i
        content = [c for c in comments[random.randint(0, num):random.randint(0, num)]]
        while not content:
            content = [c for c in comments[random.randint(0, num):random.randint(0, num)]]
        line = "INSERT INTO reviews_by_movie (movie_id, title, reviews) VALUES (" + f"{movie_id}, \'{title}\', {content}" + ");\n"
        out.write(line)

        # movies_by_directors
        line = "INSERT INTO movies_by_directors (movie_id, title, directors) VALUES (" + f"{i}, \'{title}\', {directors}" + ");\n"
        out.write(line)

        # movies_by_finances
        line = "INSERT INTO movies_by_finances (movie_id, title, finances) VALUES (" + f"{i}, \'{title}\', {finances}" + ");\n"
        out.write(line)

        # movies_by_producers
        line = "INSERT INTO movies_by_producers (movie_id, title, producers) VALUES (" + f"{i}, \'{title}\', {producers}" + ");\n"
        out.write(line)
