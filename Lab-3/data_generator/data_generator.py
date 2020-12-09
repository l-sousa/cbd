import random
import re
from calendar import monthrange

def get_random_timestamp():
    date = "2020-12-" + str(random.randint(1,31)) + "T" + str(random.randint(10,23)) + ":" + str(random.randint(10,59)) + ":" + str(random.randint(10,59)) + "." + str(random.randint(100,999)) + "+0000"
    return date

def deEmojify(text):
    regrex_pattern = re.compile(pattern = "["
        u"\U0001F600-\U0001F64F"  # emoticons
        u"\U0001F300-\U0001F5FF"  # symbols & pictographs
        u"\U0001F680-\U0001F6FF"  # transport & map symbols
        u"\U0001F1E0-\U0001F1FF"  # flags (iOS)
                           "]+", flags = re.UNICODE)
    return regrex_pattern.sub(r'',text)


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

    #user
    for i in range(num):
        line = "INSERT INTO user (username, name, email, regist_time) VALUES ('{}', '{}', '{}', '{}');".format(usernames[i], picked_names[i], emails[i], get_random_timestamp())
        out.write(line)
    

    #video
    for i in range(num):
        rand_email = random.choice(emails)
        line = "INSERT INTO video (author, id, title, upload_time, tag) VALUES ('{}', '{}', '{}', '{}', '{}');".format(rand_email, vid_ids[i],  "title " + str(i), get_random_timestamp(), random.choice(vid_tags))
        out.write(line)

        line = "INSERT INTO video_author (email, vid_id) VALUES ('{}', '{}');".format(rand_email, vid_ids[i])
        out.write(line)

    for i in range(num):
        line = "INSERT INTO comment (id, vid_id, author, content, comment_time) VALUES (now(), '{}', '{}', '{}', '{}'); ".format(random.choice(vid_ids), random.choice(emails),random.choice(comments), get_random_timestamp())
        out.write(line)
    
    
    for i in range(num):
        line = "INSERT INTO following (user, vid_id) VALUES ('{}', '{}');".format(random.choice(emails), random.choice(vid_ids))    
        out.write(line)
    

    for i in range(num):
        line = "INSERT INTO event (id, user, vid_id, event, event_timestamp, video_timestamp) VALUES (now(), '{}', '{}', '{}', '{}', '{}');".format(random.choice(emails), random.choice(vid_ids),random.choice(
            ["play", "pause", "fast-forward", "fast-backward", "captions-on", "captions-off","change-resolution"]), get_random_timestamp(), get_random_timestamp())
        out.write(line)
    

    for i in range(num):
        line = "INSERT INTO rating (id, value, vid_id) VALUES (now(), {}, '{}');".format(random.randint(0,10), random.choice(vid_ids))    
        out.write(line)
    


for i in comments:
    print(i)