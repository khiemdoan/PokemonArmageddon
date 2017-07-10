import os
import sqlite3
import threading
import re
import scrapy
from scrapy.crawler import CrawlerProcess


db_path = 'pokemon_armageddon.db'

urls = [
	'https://confidencewriter.wordpress.com/pokemon-armageddon/',
	
    'https://confidencewriter.wordpress.com/pokemon-armageddon/pokemon-armageddon-chapter-1/',
    'https://confidencewriter.wordpress.com/pokemon-armageddon/pokemon-armageddon-chapter-2/',
    'https://confidencewriter.wordpress.com/pokemon-armageddon/pokemon-armageddon-chapter-3/',
    'https://confidencewriter.wordpress.com/pokemon-armageddon/pokemon-armageddon-chapter-4/',
    'https://confidencewriter.wordpress.com/pokemon-armageddon/pokemon-armageddon-chapter-5/',
    'https://confidencewriter.wordpress.com/pokemon-armageddon/pokemon-armageddon-chapter-6/',
    'https://confidencewriter.wordpress.com/pokemon-armageddon/pokemon-armageddon-chapter-7/',
    'https://confidencewriter.wordpress.com/pokemon-armageddon/pokemon-armageddon-chapter-8/',
    'https://confidencewriter.wordpress.com/pokemon-armageddon/pokemon-armageddon-chapter-9/',
    'https://confidencewriter.wordpress.com/pokemon-armageddon/pokemon-armageddon-chapter-10/',

    'https://confidencewriter.wordpress.com/pokemon-armageddon/pokemon-armageddon-chapter-11/',
    'https://confidencewriter.wordpress.com/pokemon-armageddon/pokemon-armageddon-chapter-12/',
    'https://confidencewriter.wordpress.com/pokemon-armageddon/pokemon-armageddon-chapter-13/',
    'https://confidencewriter.wordpress.com/pokemon-armageddon/pokemon-armageddon-chapter-14/',
    'https://confidencewriter.wordpress.com/pokemon-armageddon/pokemon-armageddon-chapter-15/',
    'https://confidencewriter.wordpress.com/pokemon-armageddon/pokemon-armageddon-chapter-16/',
    'https://confidencewriter.wordpress.com/pokemon-armageddon/pokemon-armageddon-chapter-17/',
    'https://confidencewriter.wordpress.com/pokemon-armageddon/pokemon-armageddon-chapter-18/',
    'https://confidencewriter.wordpress.com/pokemon-armageddon/pokemon-armageddon-chapter-19/',
    'https://confidencewriter.wordpress.com/pokemon-armageddon/pokemon-armageddon-chapter-20/',

    'https://confidencewriter.wordpress.com/pokemon-armageddon/pokemon-armageddon-chapter-21/',
    'https://confidencewriter.wordpress.com/pokemon-armageddon/pokemon-armageddon-chapter-22/',
    'https://confidencewriter.wordpress.com/pokemon-armageddon/pokemon-armageddon-chapter-23/',
    'https://confidencewriter.wordpress.com/pokemon-armageddon/pokemon-armageddon-chapter-24/',
    'https://confidencewriter.wordpress.com/pokemon-armageddon/pokemon-armageddon-chapter-25/',
    'https://confidencewriter.wordpress.com/pokemon-armageddon/pokemon-armageddon-chapter-26/',
    'https://confidencewriter.wordpress.com/pokemon-armageddon/pokemon-armageddon-chapter-27/',
    'https://confidencewriter.wordpress.com/pokemon-armageddon/pokemon-armageddon-chapter-28/',
    'https://confidencewriter.wordpress.com/pokemon-armageddon/pokemon-armageddon-chapter-29/',
    'https://confidencewriter.wordpress.com/pokemon-armageddon/pokemon-armageddon-chapter-30/',

    'https://confidencewriter.wordpress.com/pokemon-armageddon/pokemon-armageddon-chapter-31/',
    'https://confidencewriter.wordpress.com/pokemon-armageddon/pokemon-armageddon-chapter-32/',
    'https://confidencewriter.wordpress.com/pokemon-armageddon/pokemon-armageddon-chapter-33/',
    'https://confidencewriter.wordpress.com/pokemon-armageddon/pokemon-armageddon-chapter-34/',
    'https://confidencewriter.wordpress.com/pokemon-armageddon/pokemon-armageddon-chapter-35/',
    'https://confidencewriter.wordpress.com/pokemon-armageddon/pokemon-armageddon-chapter-36/',
    'https://confidencewriter.wordpress.com/pokemon-armageddon/pokemon-armageddon-chapter-37/',
    'https://confidencewriter.wordpress.com/pokemon-armageddon/pokemon-armageddon-chapter-38/',
    'https://confidencewriter.wordpress.com/pokemon-armageddon/pokemon-armageddon-chapter-39/',
    'https://confidencewriter.wordpress.com/pokemon-armageddon/pokemon-armageddon-chapter-40/',

    'https://confidencewriter.wordpress.com/pokemon-armageddon/pokemon-armageddon-chapter-41/',
    'https://confidencewriter.wordpress.com/pokemon-armageddon/pokemon-armageddon-chapter-42/',
    'https://confidencewriter.wordpress.com/pokemon-armageddon/pokemon-armageddon-chapter-43/',
    'https://confidencewriter.wordpress.com/pokemon-armageddon/pokemon-armageddon-chapter-43-2/',
    'https://confidencewriter.wordpress.com/pokemon-armageddon/pokemon-armageddon-chapter-44/',
    'https://confidencewriter.wordpress.com/pokemon-armageddon/pokemon-armageddon-chapter-45/',
    'https://confidencewriter.wordpress.com/pokemon-armageddon/pokemon-armageddon-chapter-46/',
    'https://confidencewriter.wordpress.com/pokemon-armageddon/pokemon-armageddon-chapter-47/',
    'https://confidencewriter.wordpress.com/pokemon-armageddon/pokemon-armageddon-chapter-48/',
    'https://confidencewriter.wordpress.com/pokemon-armageddon/pokemon-armageddon-chapter-49/',
    'https://confidencewriter.wordpress.com/pokemon-armageddon/pokemon-armageddon-chapter-50/',

    'https://confidencewriter.wordpress.com/pokemon-armageddon/pokemon-armageddon-chapter-51/',
    'https://confidencewriter.wordpress.com/pokemon-armageddon/pokemon-armageddon-chapter-52/',
    'https://confidencewriter.wordpress.com/pokemon-armageddon/pokemon-armageddon-chapter-53/',
    'https://confidencewriter.wordpress.com/pokemon-armageddon/pokemon-armageddon-chapter-54-the-final-battle-i/',
    'https://confidencewriter.wordpress.com/pokemon-armageddon/pokemon-armageddon-chapter-55-the-final-battle-ii/',
    'https://confidencewriter.wordpress.com/pokemon-armageddon/pokemon-armageddon-chapter-56-the-final-battle-iii/',
    'https://confidencewriter.wordpress.com/pokemon-armageddon/pokemon-armageddon-chapter-57-the-final-battle-iv/',
    'https://confidencewriter.wordpress.com/pokemon-armageddon/pokemon-armageddon-chapter-58-the-final-battle-v/',
    'https://confidencewriter.wordpress.com/pokemon-armageddon/pokemon-armageddon-chapter-59-the-final-battle-vi/',
    'https://confidencewriter.wordpress.com/pokemon-armageddon/pokemon-armageddon-chapter-60-the-final-battle-vii/',

    'https://confidencewriter.wordpress.com/pokemon-armageddon/pokemon-armageddon-chapter-61-the-final-battle-viii/',
    'https://confidencewriter.wordpress.com/pokemon-armageddon/pokemon-armageddon-chapter-62-the-final-battle-ix/',
    'https://confidencewriter.wordpress.com/pokemon-armageddon/pokemon-armageddon-chapter-63-the-final-battle-x/',

    'https://confidencewriter.wordpress.com/pokemon-armageddon/pokemon-armageddon-specials-illuminati/',
    'https://confidencewriter.wordpress.com/pokemon-armageddon/pokemon-armageddon-dragon-dynasty-promo/',
]


class PokemonArmageddonDB:

    def __init__(self):
        # delete database if exist
        if os.path.exists(db_path):
            os.remove(db_path)

        # create new database
        self._conn = sqlite3.connect(db_path)
        self._cursor = self._conn.cursor()

        self._lock = threading.Lock()

        # create table
        sql_cmd = 'CREATE TABLE chapters (id INTEGER PRIMARY KEY AUTOINCREMENT, title VARCHAR, content TEXT);'
        self._lock.acquire()
        self._cursor.execute(sql_cmd)
        self._lock.release()

    def __del__(self):
        self._conn.commit()
        self._conn.close()

    def add_chapter(self, title, content):
        sql_cmd = 'INSERT INTO chapters (title, content) VALUES (?, ?);'
        self._lock.acquire()
        self._cursor.execute(sql_cmd, (title, content))
        self._lock.release()


class PokemonArmageddonSpider(scrapy.Spider):

    name = 'PokemonArmageddonSpider'

    def __init__(self):
        super(PokemonArmageddonSpider, self).__init__()
        self._db = PokemonArmageddonDB()

    def start_requests(self):
        # crawl first url
        url = urls.pop(0)
        return [scrapy.Request(url=url)]

    def parse(self, response):

        # get content
        nodes = response.xpath('//div[@class="entry-content"]/node()').extract()
        nodes = filter(lambda node: not re.match(r'[\t\n\r]+', node), nodes)
        nodes = filter(lambda node: not re.match(r'<div[^>]*>.*</div>', node), nodes)
        nodes = map(lambda node: re.sub(r'—{20,}', '-' * 40, node), nodes)
        content = ''.join(list(nodes))

        # get title
        title = response.xpath('//h1[@class="entry-title"]/node()[1]').extract()
        if len(title):
            title = re.sub(r'.*\s\W{1,2}', '', title[0])
        else:
            title = ''

        print(title)

        # insert to database
        self._db.add_chapter(title, content)

        # crawl next url
        if urls:
            return scrapy.Request(url=urls.pop(0))


if __name__ == '__main__':
    process = CrawlerProcess({
        'USER_AGENT': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.19 Safari/537.36'
    })
    process.crawl(PokemonArmageddonSpider)
    process.start()
