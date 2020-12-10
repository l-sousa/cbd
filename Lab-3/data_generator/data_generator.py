import random
import re
from calendar import monthrange

def get_random_timestamp():
    date = "2020-12-" + str(random.randint(1,31)) + "T" + str(random.randint(10,23)) + ":" + str(random.randint(10,59)) + ":" + str(random.randint(10,59)) + "." + str(random.randint(100,999)) + "+0000"
    return date

fnames = []
lnames = []

num = 30
picked_names = []
emails = []
usernames = []
vid_tags = []
ratings = []
comments = []
vid_ids = []

with open("tags.txt") as t:
    for line in t:
        vid_tags.append(line.strip())

with open("fnames.txt") as first:
    with open("lnames.txt") as last:
        for fname in first:
            fnames.append(fname)
        for lname in last:
            lnames.append(lname)  

with open("vid_ids.txt") as vid:
    for line in vid:
        vid_ids.append(line.strip())

with open("comments.txt") as c:
    for line in c:
        l = line.split(",")
        if len(l) == 4 and l:
            comment = ''.join([i if i == " " or ord(i) < 128 and ord(i) >46 else '' for i in l[1].strip()]).replace('[',"").replace(']w',"")
            if len(comment) > 10 and  len(comment) < 40:
                comments.append(comment)


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
    #line = "INSERT INTO user () VALUES ('{}');\n".format()

    for i in range(num):
        #user
        line = "INSERT INTO user (username, name, email, regist_time) VALUES ('{}', '{}', '{}', '{}');".format(usernames[i], picked_names[i], emails[i], get_random_timestamp())
        out.write(line)
    
        #video
        tag = random.choice(vid_tags)
        rand_email = random.choice(emails)
        t = get_random_timestamp()
        line = "INSERT INTO video (author, id, title, upload_time, tag) VALUES ('{}', '{}', '{}', '{}', '{}');".format(rand_email, vid_ids[i],  "title " + str(i), t, tag)
        out.write(line)

        #video_author
        line = "INSERT INTO video_author (email, vid_id, comment_time) VALUES ('{}', '{}', '{}');".format(rand_email, vid_ids[i], t)
        out.write(line)

        #video_author
        line = "INSERT INTO video_tag (tag, vid_id) VALUES ('{}', '{}');".format(tag, vid_ids[i])
        out.write(line)
        
        #comment
        rand_id = random.choice(vid_ids)
        rand_email = random.choice(emails)
        ts= get_random_timestamp()
        c = random.choice(comments)
        line = "INSERT INTO comment (id, vid_id, author, content, comment_time) VALUES ({}, '{}', '{}', '{}', '{}'); ".format(i, rand_id, rand_email,c, ts)
        out.write(line)

        #comment author
        line = "INSERT INTO comment_author (author, comment_id, comment_time, content) VALUES ('{}', {}, '{}', '{}');".format(rand_email, i, ts, c)
        out.write(line)

         #comment author
        line = "INSERT INTO comment_video (vid_id, comment_id, comment_time, content) VALUES ('{}', {}, '{}', '{}');".format(rand_id, i, ts, c)
        out.write(line)

        #following
        line = "INSERT INTO following (user, vid_id) VALUES ('{}', '{}');".format(random.choice(emails), random.choice(vid_ids))    
        out.write(line)
    
        #event
        line = "INSERT INTO event (id, user, vid_id, event, event_timestamp, video_timestamp) VALUES (now(), '{}', '{}', '{}', '{}', '{}');".format(random.choice(emails[:num//3]), random.choice(vid_ids),random.choice(
            ["play", "pause", "fast-forward", "fast-backward", "captions-on", "captions-off","change-resolution"]), get_random_timestamp(), get_random_timestamp())
        out.write(line)

        #rating
        line = "INSERT INTO rating (id, value, vid_id) VALUES (now(), {}, '{}');".format(random.randint(0,10), random.choice(vid_ids))    
        out.write(line)