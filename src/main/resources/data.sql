INSERT INTO tags VALUES ('creation'),
                        ('modification'),
                        ('design'),

                        ('website'),
                        ('webapp'),
                        ('mobile'),
                        ('desktop'),
                        ('embedded'),
                        ('database'),
                        ('user experience'),

                        ('game'),
                        ('business'),
                        ('data analysis'),
                        ('high performance') ON CONFLICT DO NOTHING;
